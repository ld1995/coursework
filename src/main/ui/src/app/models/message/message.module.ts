import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: []
})
export class MessageModule {
  author: string;
  timeCreation: string;
  content: string;
  chatId: string;
}
