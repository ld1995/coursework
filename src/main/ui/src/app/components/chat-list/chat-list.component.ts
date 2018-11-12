import {Component, Input, OnInit} from '@angular/core';
import {ChatModule} from "../../models/chat/chat.module";

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.sass']
})
export class ChatListComponent implements OnInit {

  @Input() public chatList: ChatModule[] = [];

  constructor() {
  }

  ngOnInit() {
  }

}
