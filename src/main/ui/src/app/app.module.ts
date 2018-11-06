import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainComponent} from './components/main/main.component';
import {AuthService} from './services/auth/auth.service';
import {LoginComponent} from './components/login/login.component';
import {TokenStorage} from "./services/storages/token.storage.service";
import {WorkplaceComponent} from './components/workplace/workplace.component';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import { SignupComponent } from './components/signup/signup.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    WorkplaceComponent,
    SignupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    AuthService,
    TokenStorage
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
