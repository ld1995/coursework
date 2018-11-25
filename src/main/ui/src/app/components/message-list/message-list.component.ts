import {AfterViewChecked, Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {MessageModule} from "../../models/message/message.module";
import {UserModule} from "../../models/user/user.module";
import {UserDataService} from "../../services/user-data/user-data.service";

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.sass']
})
export class MessageListComponent implements OnInit, AfterViewChecked {

  @Input() public messageList: MessageModule[] = [];
  @Input() public me: UserModule;
  @Input() public id: string;
  @ViewChild('scrollBottom') private scrollBottom: ElementRef;


  constructor(private userData: UserDataService) {
    this.userData.message$.subscribe(message => {
      if (message.chatId == this.id) {
        this.messageList.push(message);
      }
    });
    this.me = this.userData.me;
  }

  ngOnInit() {
    this.scrollToBottom();
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.scrollBottom.nativeElement.scrollTop = this.scrollBottom.nativeElement.scrollHeight;
    } catch (err) {
    }
  }
}
