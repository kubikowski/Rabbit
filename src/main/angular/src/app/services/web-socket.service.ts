import { Injectable, OnInit, OnDestroy } from '@angular/core';
import { Subscriptions, Subscription, MultiSubscription } from './web-socket-subscription.model';
import { StompService } from './stomp.service';


@Injectable({ providedIn: 'root' })
export class WebSocketService implements OnInit, OnDestroy {

  readonly REGISTRATION_URL = "/ws-register";
  readonly notifiableMessageTimeout = 4500;
  readonly maxReconnectAttempts = 20;

  connected = false;
  reconnectAttempts = 0;

  subscriptions: Subscriptions = {};

  constructor(private stompService: StompService) { }

  ngOnInit(): void {
    this.init();
    console.log('initialised connection');
  }

  ngOnDestroy(): void {
    this.clear();
  }

  init(debugFunction = null): Promise<any> {
    return this.initialize(debugFunction);
  }

  initialize(debugFunction = null): Promise<any> {
    this.stompService.setDebug(debugFunction);
    return this.connect();
  }

  private connect(): Promise<any> {
    return this.stompService.connect(this.REGISTRATION_URL, {}, this.disconnectErrorHandler.bind(this)).then(() => {
      this.connected = true;
      Object.entries<Subscription | MultiSubscription>(this.subscriptions)
            .forEach(([subscriptionDestination, subscription]) => {
        if (subscription instanceof MultiSubscription) {
          Object.entries(subscription).forEach(([subscriptionKey, multiSubscription]) => {
            if (typeof multiSubscription.callback !== 'undefined') {
              this.multiSubscribe(subscriptionDestination, multiSubscription.callback, subscriptionKey);
            }
          });
        } else {
          this.subscribe(subscriptionDestination, subscription.callback);
        }
      });
    });
  }

  private disconnectErrorHandler(error) {
    this.connected = false;
    this.reconnectAttempts++;
    if (this.reconnectAttempts <= this.maxReconnectAttempts) {
      setTimeout(() => {
        if (!this.connected) {
          this.connect();
        }
      }, 5000);
    } else {
      // this.notificationService.errorToastAndNotify("Server unreachable. Please contact your administrator.");
      this.clear();
    }
  }

  clear() {
    this.connected = false;
    this.unsubscribeAll();

    if (Object.keys(this.subscriptions).length !== 0) {
      console.error('subscriptions should be empty');
    }

    try {
      this.stompService.disconnect();
    } catch (e) { }
  }

  private newSubscription(subscriptionDestination, subscribeCallback): Subscription {
    let connection;

    if (this.connected) {
      try {
        connection = this.stompService.subscribe(subscriptionDestination, (response) => {
          if (response.notifiable) {
            // this.notificationService.infoToast(response.message);
            setTimeout(() => {
              subscribeCallback(response.body);
            }, this.notifiableMessageTimeout);
          } else {
            subscribeCallback(response.body);
          }
        });
      } catch (e) { }
    }

    return new Subscription(connection, subscribeCallback);
  }

  subscribe(subscriptionDestination: string, subscribeCallback: () => any): Promise<void> {
    this.subscriptions[subscriptionDestination] = this.newSubscription(subscriptionDestination, subscribeCallback);
    return Promise.resolve();
  }

  multiSubscribe(subscriptionDestination: string, subscribeCallback: () => any, subscriptionKey: string): Promise<void> {
    const subscription = this.newSubscription(subscriptionDestination, subscribeCallback);

    if (typeof this.subscriptions[subscriptionDestination] === 'undefined') {
      this.subscriptions[subscriptionDestination] = new MultiSubscription();
    }
    this.subscriptions[subscriptionDestination][subscriptionKey] = subscription;

    return Promise.resolve();
  }

  unsubscribe(subscriptionDestination: string) {
    if (this.subscriptions.hasOwnProperty(subscriptionDestination)) {
      const subscription = this.subscriptions[subscriptionDestination];

      if (subscription instanceof Subscription) {
        if (subscription.connection) {
          subscription.connection.unsubscribe();
        }

        delete this.subscriptions[subscriptionDestination];
      } else if (subscription instanceof MultiSubscription) {
        console.warn(`Improper use of webSocketService.unsubscribe(). Did you mean to use webSocketService.multiUnsubscribe()?`);
      } else {
        console.warn(`Improper use of webSocketService.unsubscribe(). Unknown subscription type.`);
      }
    }
  }

  multiUnsubscribe(subscriptionDestination, subscriptionKey) {
    if (this.subscriptions.hasOwnProperty(subscriptionDestination)) {
      const subscription = this.subscriptions[subscriptionDestination];

      if (subscription instanceof MultiSubscription) {
        if (subscription.hasOwnProperty(subscriptionKey)) {
          subscription[subscriptionKey].connection.unsubscribe();
          delete subscription[subscriptionKey];
        }

        if (Object.keys(subscription).length === 0) {
          delete this.subscriptions[subscriptionDestination];
        }
      } else if (subscription instanceof Subscription) {
        console.warn(`Improper use of webSocketService.multiUnsubscribe(). Did you mean to use webSocketService.unsubscribe()?`);
      } else {
        console.warn(`Improper use of webSocketService.multiUnsubscribe(). Unknown subscription type.`);
      }
    }
  }

  private unsubscribeAll() {
    Object.entries<Subscription | MultiSubscription>(this.subscriptions)
          .forEach(([subscriptionDestination, subscription]) => {
      if (subscription instanceof Subscription) {
        this.unsubscribe(subscriptionDestination);
      } else if (subscription instanceof MultiSubscription) {
        Object.keys(subscription)
              .forEach(subscriptionKey => this.multiUnsubscribe(subscriptionDestination, subscriptionKey));
      } else {
        console.warn('Unknown subscription type');
        console.info({subscription});
      }
    });
  }
}
