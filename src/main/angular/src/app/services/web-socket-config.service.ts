import { Injectable } from '@angular/core';

enum QueueName {
  DEFAULT = '',
  HELLO = 'hello',
  TASK_QUEUE = 'task_queue',
}

enum ExchangeName {
  DEFAULT = '',
  LOGS = 'logs',
}

@Injectable({ providedIn: 'root' })
export class WebSocketConfigService {

  readonly QueueName = QueueName;
  readonly ExchangeName = ExchangeName;

  readonly REGISTRATION_URL = "/ws-register";
  readonly notifiableMessageTimeout = 4500;
  readonly maxReconnectAttempts = 20;

  constructor() { }

}
