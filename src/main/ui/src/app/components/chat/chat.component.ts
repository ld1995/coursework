import {Component, Input, OnInit} from '@angular/core';
import {ChatModule} from "../../models/chat/chat.module";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.sass']
})
export class ChatComponent implements OnInit {

  @Input() chat: ChatModule;

  constructor() {
  }

  ngOnInit() {
  }

}
