import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserModule} from "../user/user.module";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: []
})
export class MessageModule {
  author: UserModule;
  timeCreation: string;
  content: string;
  chatId: string;
}
