import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { PromiseService } from "./services/promise.service";
import { StompService } from "./services/stomp.service";
import { WebSocketConfigService } from "./services/web-socket-config.service";
import { WebSocketService } from "./services/web-socket.service";

import { ReceiveLogsComponent } from './components/receive-logs/receive-logs.component';
import { SendComponent } from './components/hello/send/send.component';

@NgModule({
  declarations: [
    AppComponent,
    ReceiveLogsComponent,
    SendComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
  ],
  providers: [
    PromiseService,
    StompService,
    WebSocketConfigService,
    WebSocketService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
