import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserModule} from "../user/user.module";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: []
})
export class ChatModule {
  author: UserModule;
  closeAt: string;
  createdAt: string;
  id: number;
  name: string;
  participants: UserModule[];
}
