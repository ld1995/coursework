import {Component} from '@angular/core';
import {TokenStorage} from "./services/storages/token.storage.service";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "./services/auth/auth.service";
import {ToastrManager} from "ng6-toastr-notifications";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {

  constructor(public tokenStorage: TokenStorage, private route: ActivatedRoute, private authService: AuthService,
              public toastr: ToastrManager) {
    this.route.queryParams.subscribe(params => {
      if (params['token'] !== undefined && params['token'] !== '') {
        this.authService.registrationConfirm(params['token']).subscribe(
          result => {
            if (!result['success']) {
              this.toastr.errorToastr(result['message'], 'Oops!');
            }
            this.toastr.successToastr(result['message'], 'Success!');
          },
          response => this.toastr.errorToastr(response.error.message, 'Oops!'));
      }
    });
  }

  logout() {
    this.tokenStorage.signOut();
  }
}
