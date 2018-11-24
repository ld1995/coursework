import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: []
})
export class ProfileModule {
  username: string;
  fullName: string;
  phoneNumber: string;
  email: string;
  password: string;
}
