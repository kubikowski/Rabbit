import { Component, OnInit } from '@angular/core';
import { WebSocketService } from "../../services/web-socket.service";

@Component({
  selector: 'app-receive-logs',
  templateUrl: './receive-logs.component.html',
  styleUrls: ['./receive-logs.component.scss']
})
export class ReceiveLogsComponent implements OnInit {

  constructor(private webSocketService: WebSocketService) { }

  ngOnInit(): void {
  }

}
