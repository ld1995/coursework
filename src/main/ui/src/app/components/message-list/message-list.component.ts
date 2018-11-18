import {Component, Input, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {MessageModule} from "../../models/message/message.module";
import {WorkplaceComponent} from "../workplace/workplace.component";
import {UserModule} from "../../models/user/user.module";

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.sass']
})
export class MessageListComponent implements OnInit {

  @Input() public messageList: MessageModule[] = [];
  @Input() public me: UserModule;

  constructor() {
  }

  ngOnInit() {
  }
}
