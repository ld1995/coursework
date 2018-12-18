import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {WorkplaceComponent} from "./components/workplace/workplace.component";
import {LoginComponent} from "./components/login/login.component";
import {AuthGuardService} from "./services/auth/auth-guard.service";
import {SignUpComponent} from "./components/signup/sign-up.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {NotAuthGuardService} from "./services/auth/not-auth-guard.service";

const routes: Routes = [
  {
    path: 'workplace',
    component: WorkplaceComponent,
    canActivate: [AuthGuardService]
  }, {
    path: 'login',
    component: LoginComponent,
    canActivate: [NotAuthGuardService]
  }, {
    path: 'signup',
    component: SignUpComponent,
    canActivate: [NotAuthGuardService]
  }, {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuardService]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
