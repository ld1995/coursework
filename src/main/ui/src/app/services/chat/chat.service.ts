import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {ChatModule} from "../../models/chat/chat.module";
import {MessageModule} from "../../models/message/message.module";
import {Cacheable} from "ngx-cacheable";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) {
  }

  public getChats(): Observable<ChatModule[]> {
    return this.http.get<ChatModule[]>(environment.baseUrl + '/chat');
  }

  public getMessage(id: number): Observable<MessageModule[]> {
    return this.http.get<MessageModule[]>(environment.baseUrl + `/message/${id}`);
  }
}
