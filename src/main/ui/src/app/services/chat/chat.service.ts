import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) {
  }

  public getChat(): Observable<Object> {
    return this.http.get(environment.baseUrl + '/chat');
  }

  public getMessage(id: number): Observable<Object> {
    return this.http.get(environment.baseUrl + `/message/${id}`);
  }
}
