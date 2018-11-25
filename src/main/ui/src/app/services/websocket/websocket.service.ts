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
  private subscriptions = [];

  constructor(private userData: UserDataService) {
  }

  connect(id: number) {
    const socket = new SockJS('http://localhost:8080/socket?jwt=' + `${localStorage.getItem('AuthToken')}`);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      let subscription = this.stompClient.subscribe(`/topic/public/${id}`, res => this.getNewChat(res));
      this.subscriptions.push(subscription);
      this.userData.chatIds.forEach(id => this.subscribeUserChat(id));
    });
  }

  createChat(data) {
    this.stompClient.send("/api/chat/create",
      {},
      JSON.stringify(data)
    );
  }

  subscribeUserChat(id: number) {
    let subscription = this.stompClient.subscribe(`/topic/public/chat/${id}`, res => this.getNewMessage(res));
    this.subscriptions.push(subscription);
  }

  sendMessage(chatId: string, content: String) {
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
    this.subscribeUserChat(newChat.id);
  }

  getNewMessage(msg: Message) {
    let newMessage: MessageModule = JSON.parse(msg.body);
    this.userData.setMessage(newMessage);
  }

  unsubscribe() {
    if (this.stompClient != null) {
      this.subscriptions.forEach(subscription => subscription.unsubscribe());
      this.subscriptions = [];
      this.stompClient.disconnect();
      this.stompClient = null;
    }
  }
}
