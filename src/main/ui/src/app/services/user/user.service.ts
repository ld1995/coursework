import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  public getUser(): Observable<Object> {
    return this.http.get(environment.baseUrl + '/user/me');
  }

  public checkUsernameAvailability(username) {
    return this.http.get(environment.baseUrl + '/user/checkUsernameAvailability', username);
  }

  public checkEmailAvailability(email): Observable<Object> {
    return this.http.get(environment.baseUrl + '/user/checkEmailAvailability', email);
  }
}
