import {Injectable} from '@angular/core';
import {Stomp} from "@stomp/stompjs"
import * as SockJS from 'sockjs-client';
import {Message} from "stompjs";
import {ChatModule} from "../../models/chat/chat.module";
import {MessageModule} from "../../models/message/message.module";
import {UserDataService} from "../user-data/user-data.service";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient = null;

  constructor(private userData: UserDataService) {
  }

  connect(id: number) {
    const socket = new SockJS('http://localhost:8080/socket?jwt=' + `${localStorage.getItem('AuthToken')}`);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(`/topic/public/${id}`, res => this.getNewChat(res));
      this.stompClient.subscribe(`/topic/public/chat`, res => this.getNewMessage(res));

    });
  }

  createChat(data) {
    this.stompClient.send("/api/chat/create",
      {},
      JSON.stringify(data)
    );
  }

  sendMessage(chatId: number, content: String) {
    this.stompClient.send(`/api/chat`,
      {},
      JSON.stringify({
        content,
        chatId
      })
    );
  }

  getNewChat(msg: Message) {
    let newChat: ChatModule = JSON.parse(msg.body);
    this.userData.setChat(newChat);
  }

  getNewMessage(msg: Message) {
    let newMessage: MessageModule = JSON.parse(msg.body);
    console.log(newMessage);
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
  }
}
