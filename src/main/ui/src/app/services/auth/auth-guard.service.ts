import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {TokenStorage} from "../storages/token.storage.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(public tokenStorage: TokenStorage, public router: Router) {
  }

  canActivate(): boolean {
    if (!this.tokenStorage.isAuthenticated()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
