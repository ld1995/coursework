import { Component } from '@angular/core';
import {TokenStorage} from "./services/storages/token.storage.service";
import {UserService} from "./services/user/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {

  constructor(public tokenStorage: TokenStorage, private userService: UserService) {

  }

  logout() {
    this.tokenStorage.signOut()
  }
}
