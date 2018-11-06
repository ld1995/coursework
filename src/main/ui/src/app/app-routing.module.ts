import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {WorkplaceComponent} from "./components/workplace/workplace.component";
import {LoginComponent} from "./components/login/login.component";
import {AuthGuardService} from "./services/auth/auth-guard.service";
import {SignupComponent} from "./components/signup/signup.component";

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
    component: SignupComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
