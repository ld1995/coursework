import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {WorkplaceComponent} from "./components/workplace/workplace.component";
import {LoginComponent} from "./components/login/login.component";
import {AuthGuardService} from "./services/auth/auth-guard.service";
import {SignUpComponent} from "./components/signup/sign-up.component";
import {ProfileComponent} from "./components/profile/profile.component";

const routes: Routes = [
  {
    path: 'workplace',
    component: WorkplaceComponent,
    canActivate: [AuthGuardService]
  }, {
    path: 'login',
    component: LoginComponent
  }, {
    path: 'signup',
    component: SignUpComponent
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
