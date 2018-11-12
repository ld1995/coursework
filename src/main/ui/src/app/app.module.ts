import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainComponent} from './components/main/main.component';
import {AuthService} from './services/auth/auth.service';
import {LoginComponent} from './components/login/login.component';
import {TokenStorage} from "./services/storages/token.storage.service";
import {WorkplaceComponent} from './components/workplace/workplace.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import {SignupComponent} from './components/signup/signup.component';
import {ProfileComponent} from './components/profile/profile.component';
import {UserService} from "./services/user/user.service";
import {InterceptService} from "./services/intercept/intercept.service";
import {ChatListComponent} from './components/chat-list/chat-list.component';
import {ChatComponent} from './components/chat/chat.component';
import {ChatService} from "./services/chat/chat.service";
import {MessageListComponent} from './components/message-list/message-list.component';
import {MessageComponent} from './components/message/message.component';
import { CreateChatComponent } from './components/create-chat/create-chat.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    WorkplaceComponent,
    SignupComponent,
    ProfileComponent,
    ChatListComponent,
    ChatComponent,
    MessageListComponent,
    MessageComponent,
    CreateChatComponent,
  ],
  entryComponents: [
    MessageListComponent,
    CreateChatComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptService,
      multi: true
    },
    AuthService,
    UserService,
    ChatService,
    TokenStorage,
    ProfileComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
