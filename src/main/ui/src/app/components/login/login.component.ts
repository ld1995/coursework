import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {Router} from "@angular/router";
import {TokenStorage} from "../../services/storages/token.storage.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ToastrManager} from "ng6-toastr-notifications";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  messageError: string;

  constructor(private router: Router, private authService: AuthService,
              private token: TokenStorage, private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      usernameOrEmail: ['', Validators.required],
      password:  ['', Validators.required]
    });
    this.messageError = "";
  }

  ngOnInit() {
  }

  onSubmit() {
    this.authService.signIn(this.loginForm.value).subscribe(response => {
        this.token.saveToken(response['accessToken']);
        this.router.navigate(['workplace']);
        this.messageError = "";
    },response => {
      this.messageError = response.error.message;
      this.loginForm.reset();
    })
  }
}
