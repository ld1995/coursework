import { Component } from '@angular/core';
import {TokenStorage} from "./services/storages/token.storage.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {

  constructor(public tokenStorage: TokenStorage) {

  }

  logout() {
    this.tokenStorage.signOut()
  }
}
