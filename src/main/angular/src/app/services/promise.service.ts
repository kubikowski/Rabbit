import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class PromiseService {

  defer<T>() {
    return new Deferrable<T>();
  }
}

export class Deferrable<T> {
  resolve: (value?: any) => any;
  reject: (error?: any) => any;
  promise: Promise<T>;

  constructor() {
    this.promise = new Promise<T>((resolve, reject) => {
      this.resolve = resolve;
      this.reject = reject;
    });
  }
}
