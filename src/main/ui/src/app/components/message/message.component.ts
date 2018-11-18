import {Component, Input, OnInit} from '@angular/core';
import {MessageModule} from "../../models/message/message.module";
import {UserModule} from "../../models/user/user.module";

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.sass']
})
export class MessageComponent implements OnInit {

  @Input() message: MessageModule;
  @Input() user: UserModule;

  constructor() {
  }

  ngOnInit() {
  }

}
