import {Injectable} from '@angular/core';
import {Subject} from "rxjs";
import {ChatModule} from "../../models/chat/chat.module";
import {ChatService} from "../chat/chat.service";
import {UserModule} from "../../models/user/user.module";
import {UserService} from "../user/user.service";
import {MessageModule} from "../../models/message/message.module";
import {ProfileModule} from "../../models/profile/profile.module";

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  me: UserModule;
  profile: ProfileModule;
  chatIds: number[] = [];
  private chats = new Subject<ChatModule>();
  chats$ = this.chats.asObservable();
  private message = new Subject<MessageModule>();
  message$ = this.message.asObservable();

  constructor() {
  }

  setChat(chat: ChatModule) {
    this.chats.next(chat);
  }

  setMessage(message: MessageModule) {
    this.message.next(message);
  }
}
