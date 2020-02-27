import { Component, OnInit, OnDestroy } from '@angular/core';
import { WebSocketService } from "../../services/web-socket.service";
import { WebSocketConfigService } from "../../services/web-socket-config.service";

@Component({
  selector: 'app-receive-logs',
  templateUrl: './receive-logs.component.html',
  styleUrls: ['./receive-logs.component.scss']
})
export class ReceiveLogsComponent implements OnInit, OnDestroy {

  queueName:string;
  exchangeName:string;

  constructor(private webSocketService: WebSocketService,
              private webSocketConfig:WebSocketConfigService) {

    this.queueName = webSocketConfig.QueueName.DEFAULT;
    this.exchangeName = webSocketConfig.ExchangeName.LOGS;
  }

  ngOnInit(): void {
    this.webSocketService.subscribe(this.queueName, this.subscribeCallback)
      .then(r => console.log(`hahaha ${r}`));
  }

  ngOnDestroy(): void {
    this.webSocketService.unsubscribe(this.queueName);
  }

  private subscribeCallback():void {
    console.log('how message??');
  }

}
