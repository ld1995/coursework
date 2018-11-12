import {Component, Input, OnInit} from '@angular/core';
import {MessageModule} from "../../models/message/message.module";
import {ProfileComponent} from "../profile/profile.component";
import {UserModule} from "../../models/user/user.module";

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.sass']
})
export class MessageComponent implements OnInit {

  @Input() message: MessageModule;
  public user: UserModule;

  constructor(public profile: ProfileComponent) {
    this.user = this.profile.user
  }

  ngOnInit() {
  }

}
