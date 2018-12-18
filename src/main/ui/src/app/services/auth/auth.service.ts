import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  public signIn(credentials): Observable<Object> {
    return this.http.post(`${environment.baseUrl}/auth/signin`, credentials);
  }

  public signUp(userData): Observable<Object> {
    return this.http.post(`${environment.baseUrl}/auth/signup`,userData);
  }

  public registrationConfirm(token): Observable<Object> {
    return this.http.get(`${environment.baseUrl}/auth/registrationConfirm?token=${token}`)
  }
}
