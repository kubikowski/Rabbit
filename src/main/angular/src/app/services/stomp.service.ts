import { Injectable } from '@angular/core';
import {PromiseService} from "./promise.service";

import { Stomp } from 'stompjs/lib/stomp.js';
import * as SockJS from 'sockjs-client/dist/sockjs.js';
import { SubscriptionLike } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class StompService {

  sock = null;
  stomp = null;
  debug = null;

  constructor(private promiseService: PromiseService) { }

  setDebug(callback) {
    this.debug = callback
  }

  connect(endpoint, headers, errorCallback: (error: Error) => any, sockjsOpts?): Promise<any> {
    headers = headers || {};
    sockjsOpts = sockjsOpts || {};

    const deferred = this.promiseService.defer();

    this.sock = new SockJS(endpoint, null, sockjsOpts);

    this.sock.onclose = () => {
      if (errorCallback) {
        errorCallback(new Error('Connection broken'))
      }
    };

    this.stomp = Stomp.over(this.sock);
    this.stomp.debug = this.debug;

    this.stomp.connect(headers, function (frame) {
      deferred.resolve(frame);
    }, function (err) {
      deferred.reject(err);
      if (errorCallback) {
        errorCallback(err);
      }
    });

    return deferred.promise;
  }

  disconnect(): Promise<any> {
    const deferred = this.promiseService.defer();
    this.stomp.disconnect(deferred.resolve);
    return deferred.promise
  }

  subscribe(destination, callback, headers?): SubscriptionLike {
    headers = headers || {};
    return this.stomp.subscribe(destination, function (res) {
      var payload = null;
      try {
        payload = JSON.parse(res.body)
      } finally {
        if (callback) {
          callback(payload, res.headers, res)
        }
      }
    }, headers);
  }

  unsubscribe(subscription) {
    subscription.unsubscribe();
  }

  send(destination, body, headers): Promise<any> {
    const deferred = this.promiseService.defer();

        try {
          const payloadJson = JSON.stringify(body);
          headers = headers || {};
          this.stomp.send(destination, headers, payloadJson);
          deferred.resolve();
        } catch (e) {
          deferred.reject(e);
        }

        return deferred.promise;
  }

}
