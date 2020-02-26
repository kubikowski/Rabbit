import { Component, OnInit } from '@angular/core';
import {WebSocketService} from "../../../services/web-socket.service";

@Component({
  selector: 'app-send',
  templateUrl: './send.component.html',
  styleUrls: ['./send.component.scss']
})

export class SendComponent implements OnInit {

  constructor(private webSocket: WebSocketService) { }

  ngOnInit(): void {
  }

}
