import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {UserModule} from "../../models/user/user.module";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  public getMe(): Observable<UserModule> {
    return this.http.get<UserModule>(environment.baseUrl + '/user/me');
  }

  public getParticipants(): Observable<UserModule[]> {
    return this.http.get<UserModule[]>(environment.baseUrl + '/users');
  }

  public checkUsernameAvailability(username) {
    return this.http.get(environment.baseUrl + '/user/checkUsernameAvailability', username);
  }

  public checkEmailAvailability(email): Observable<Object> {
    return this.http.get(environment.baseUrl + '/user/checkEmailAvailability', email);
  }
}
