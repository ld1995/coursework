import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../services/user/user.service";
import {UserModule} from "../../models/user/user.module";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class ProfileComponent implements OnInit, OnDestroy {

  user: UserModule;

  constructor(private userService: UserService) {
    this.userService.getMe().subscribe(response => {
      this.user = response;
    })
  }

  ngOnInit() {

  }

  ngOnDestroy() {

  }

}
