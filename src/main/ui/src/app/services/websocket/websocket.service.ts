import {Injectable} from '@angular/core';
import {Stomp} from "@stomp/stompjs"
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient = null;
  // private header = {
  //   headers: {
  //     Authorization: `Bearer ${localStorage.getItem('AuthToken')}`
  //   }
  // };

  //ws://localhost:8080/socket/topic/public/chat.create
  constructor() {
    this.connect();
  }

  connect() {
    const socket = new SockJS('http://localhost:8080/socket?jwt='+`${localStorage.getItem('AuthToken')}`);
    //Authorization: `Bearer ${localStorage.getItem('AuthToken')}`
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => this.onConnected, this.onError);
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
  }

  onConnected() {
    console.log('123');
    this.stompClient.subscribe('/topic/public', this.onMessageReceived);
  }

  sendMessage(name: String) {
    console.log('message', this.stompClient.status);
    // if (this.stompClient.status === 'CONNECTED') {
      console.log('CONNECTED', name);
      this.stompClient.send("/api/chat/create",
        {},
        JSON.stringify({
          name: name,
          closeAt: new Date()
        })
      );
    // }
  }

  onError(error) {
    console.log(error)
  }

  onMessageReceived(payload) {
    console.log(payload)
  }
}

// sendMessage() {
//   // var messageContent = messageInput.value.trim();
//   if(messageContent && stompClient) {
//     // var chatMessage = {
//     //   sender: username,
//     //   content: messageInput.value,
//     //   type: 'CHAT'
//     // };
//     this.stompClient.send("/api/chat/`${chatId}`/sendMessage", {}, JSON.stringify(chatMessage));
//     // messageInput.value = '';
//   }
//   event.preventDefault();
// }
