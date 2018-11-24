import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {UserModule} from "../../models/user/user.module";
import {ProfileModule} from "../../models/profile/profile.module";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  public getMe(): Observable<UserModule> {
    return this.http.get<UserModule>(environment.baseUrl + '/user/me');
  }

  public getProfile(): Observable<ProfileModule> {
    return this.http.get<ProfileModule>(environment.baseUrl + '/user/profile');
  }

  public getParticipants(): Observable<UserModule[]> {
    return this.http.get<UserModule[]>(environment.baseUrl + '/users');
  }

  public checkUsernameAvailability(username) {
    return this.http.get(environment.baseUrl + '/user/checkUsernameAvailability', {params : {username}});
  }

  public checkEmailAvailability(email) {
    return this.http.get(`${environment.baseUrl}/user/checkEmailAvailability`, {params : {email}});
  }
}
