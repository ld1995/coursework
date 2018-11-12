import {Component, ComponentFactoryResolver, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {WebSocketService} from "../../services/websocket/websocket.service";
import {ChatService} from "../../services/chat/chat.service";
import {ChatModule} from "../../models/chat/chat.module";
import {MessageListComponent} from "../message-list/message-list.component";
import {ProfileComponent} from "../profile/profile.component";
import {CreateChatComponent} from "../create-chat/create-chat.component";

@Component({
  selector: 'app-workplace',
  templateUrl: './workplace.component.html',
  styleUrls: ['./workplace.component.sass']
})
export class WorkplaceComponent implements OnInit {

  public chatList: ChatModule[]= [];
  public filteredItems: ChatModule[]= [];
  @ViewChild('messagecontainer', {read: ViewContainerRef}) entry: ViewContainerRef;
  showCloseButton = false;

  constructor(private chatService: ChatService,  private resolver: ComponentFactoryResolver, public profile: ProfileComponent) {
    this.chatService.getChat().subscribe(response => {
      this.chatList = response;
      this.filteredItems = response;
    },response => {
      console.log(response);
    })
    //private ws: WebSocketService
    // this.ws.disconnect();
  }

  ngOnInit() {
  }

  sendMessage(message: string) {
    console.log(message);
    // this.ws.sendMessage('Hello world');
    // this.ws.sendMessage(message);
  }

  getMessagesByChatId(id: number) {
    this.chatService.getMessage(id).subscribe(response => {
      this.cleanWorkPlace();
      const toolFactory = this.resolver.resolveComponentFactory(MessageListComponent);
      const toolComponent = this.entry.createComponent(toolFactory);
      toolComponent.instance.messageList = response;
      this.showCloseButton = true;
    }, response => {
      this.cleanWorkPlace();
      console.log(response);
    })
  }

  cleanWorkPlace() {
    this.entry.clear();
    this.showCloseButton = false;
  }

  addChat() {
    this.cleanWorkPlace();
    const toolFactory = this.resolver.resolveComponentFactory(CreateChatComponent);
    const toolComponent = this.entry.createComponent(toolFactory);
    console.log('add chat');
  }

  assignCopy(){
    this.filteredItems = Object.assign([], this.chatList);
  }

  filterItem(value){
    if(!value) this.assignCopy();
    this.filteredItems = Object.assign([], this.chatList).filter(
      item => item.name.toLowerCase().indexOf(value.toLowerCase()) > -1
    )
  }
}
