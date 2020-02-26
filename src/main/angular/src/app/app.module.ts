import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

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
    AppRoutingModule
  ],
  providers: [
    WebSocketService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
