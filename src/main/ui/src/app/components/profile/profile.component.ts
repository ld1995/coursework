import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ProfileModule} from "../../models/profile/profile.module";
import {UserDataService} from "../../services/user-data/user-data.service";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class ProfileComponent implements OnInit, OnDestroy {

  messageError: string;
  messageSuccess: string;
  profile: ProfileModule;
  profileForm: FormGroup;

  constructor(private userDataService: UserDataService, private formBuilder: FormBuilder) {
    this.profile = this.userDataService.profile;
    this.messageError = "";
    this.messageSuccess = "";

    this.profileForm = this.formBuilder.group({
      fullName: [this.profile.fullName,
        [Validators.required, Validators.maxLength(100), Validators.minLength(4)]],
      password: ['', [Validators.required, Validators.maxLength(20), Validators.minLength(6)]],
      email: [this.profile.email, [Validators.required, Validators.email, Validators.maxLength(40)]],
      phoneNumber: [this.profile.phoneNumber,
        [Validators.required, Validators.maxLength(9), Validators.minLength(9)]]
    });

  }

  ngOnInit() {

  }

  ngOnDestroy() {

  }

  updateProfile() {

  }
}
