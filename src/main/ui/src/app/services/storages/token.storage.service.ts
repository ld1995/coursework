import {Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {JwtHelperService} from '@auth0/angular-jwt';

const TOKEN_KEY = 'AuthToken';
const helper = new JwtHelperService();

@Injectable({
  providedIn: 'root'
})
export class TokenStorage {

  constructor(private router: Router) {
  }

  public signOut() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.clear();
    this.router.navigate(['']);
  }

  public saveToken(token: string) {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, token);
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem(TOKEN_KEY);
    if (token == undefined)
      return false;
    return !helper.isTokenExpired(token);
  }
}
