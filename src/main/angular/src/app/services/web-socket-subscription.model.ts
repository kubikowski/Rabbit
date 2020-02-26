import { SubscriptionLike } from 'rxjs';

/**
 * Equivalent to Map<String, Subscription or MultiSubscription>
 */
export interface Subscriptions {
  [x: string]: Subscription | MultiSubscription;
}

export class Subscription {
  constructor(public connection: SubscriptionLike,
              public callback: () => any) {
  }
}

/**
 * Equivalent to Map<String, Subscription>
 */
export class MultiSubscription {
  [x: string]: Subscription;

  constructor() {
  }
}
