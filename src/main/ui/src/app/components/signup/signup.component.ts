import {Component, OnInit} from '@angular/core';
import {AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";
import {UserService} from "../../services/user/user.service";
import {Observable} from "rxjs";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.sass']
})
export class SignupComponent implements OnInit {

  signUpForm: FormGroup;
  messageError: string;
  messageSuccess: string;

  constructor(private router: Router, private authService: AuthService,
              private formBuilder: FormBuilder, private userService: UserService) {
    this.messageError = "";
    this.messageSuccess = "";
    this.signUpForm = this.formBuilder.group({
      fullName: ['', [Validators.required, Validators.maxLength(100), Validators.minLength(4)]],
      username: ['', [Validators.required, Validators.maxLength(15), Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.maxLength(20), Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email, Validators.maxLength(40)]],
      phoneNumber: ['', [Validators.required, Validators.maxLength(9), Validators.minLength(9)]]
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    this.authService.signUp(this.signUpForm.value).subscribe(response => {
      if (!response['success']) {
        this.messageError = response['message'];
      }
      this.messageError = '';
      this.messageSuccess = response['message'];
      this.signUpForm.reset();
      this.router.navigate(['login']);
    }, response => {
      this.messageError = response.error.message;
    })
  }
}
