import {Component, ComponentFactoryResolver, OnDestroy, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {WebSocketService} from "../../services/websocket/websocket.service";
import {ChatService} from "../../services/chat/chat.service";
import {ChatModule} from "../../models/chat/chat.module";
import {CreateChatComponent} from "../create-chat/create-chat.component";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MessageListComponent} from "../message-list/message-list.component";
import {UserDataService} from "../../services/user-data/user-data.service";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-workplace',
  templateUrl: './workplace.component.html',
  styleUrls: ['./workplace.component.sass']
})
export class WorkplaceComponent implements OnInit, OnDestroy {

  @ViewChild('messagecontainer', {read: ViewContainerRef}) entry: ViewContainerRef;
  public chatList: ChatModule[] = [];
  public filteredItems: ChatModule[] = [];
  public messageForm: FormGroup;
  private showCloseButton = false;
  private showInputMessage = false;
  private selectedElement: Element = null;
  private openChatId: string;

  constructor(private userData: UserDataService, private chatService: ChatService,
              private resolver: ComponentFactoryResolver, private userService: UserService,
              private ws: WebSocketService, private formBuilder: FormBuilder) {
    this.messageForm = this.formBuilder.group({
      message: ['']
    });
    this.userService.getMe().subscribe(
      response => {
        this.userData.me = response;
        this.ws.connect(response.id)
      },
      response => console.log(response)
    );
    this.userService.getProfile().subscribe(response => this.userData.profile = response);
    this.userData.chats$.subscribe(chat => {
        this.chatList.unshift(chat);
        // if (chat.author.id = this.userData.me.id) {
        //   this.loadMessages(chat.id);
        //   document.getElementById(chat.id).classList.add('selected')
        // }
      });
    this.chatService.getChats().subscribe(
      response => response.forEach(chat => {
        this.userData.setChat(chat);
        this.userData.chatIds.push(chat.id);
      }),
      response => console.log(response));
    this.filteredItems = this.chatList;
  }

  ngOnInit() {
  }

  sendMessage() {
    if (this.openChatId !== null) {
      this.ws.sendMessage(this.openChatId, this.messageForm.value.message.trim());
      this.messageForm.reset();
    }
  }

  checkInput(): boolean {
    return this.messageForm.value.message == null || this.messageForm.value.message.trim().length == 0;
  }

  getMessagesByChatId(event) {
    let target = event.target;
    let thisElement = document.getElementsByTagName("app-chat-list")[0];
    while (target != thisElement) {
      if (target.className == 'content') {
        this.hideComponent();
        this.highlight(target);
        this.loadMessages(target.id);
        break;
      }
      target = target.parentNode;
    }
  }

  highlight(node) {
    if (this.selectedElement) {
      this.selectedElement.classList.remove('selected');
    }
    this.selectedElement = node;
    this.selectedElement.classList.add('selected');
  }

  loadMessages(id: string) {
    this.openChatId = id;
    const toolFactory = this.resolver.resolveComponentFactory(MessageListComponent);
    const toolComponent = this.entry.createComponent(toolFactory);
    toolComponent.instance.id = id;
    this.chatService.getMessage(id).subscribe(response => {
      response.forEach(message => this.userData.setMessage(message));
      this.showCloseButton = true;
      this.showInputMessage = true;
    }, response => {
      this.hideComponent();
      console.log(response);
    });
  }

  hideComponent() {
    this.openChatId = null;
    this.messageForm.reset();
    this.entry.clear();
    this.showCloseButton = false;
    this.showInputMessage = false;
    if (this.selectedElement) {
      this.selectedElement.classList.remove('selected');
    }
  }

  showComponentCreateChat() {
    this.hideComponent();
    const toolFactory = this.resolver.resolveComponentFactory(CreateChatComponent);
    const toolComponent = this.entry.createComponent(toolFactory);
    this.showCloseButton = true;
  }

  assignCopy() {
    this.filteredItems = Object.assign([], this.chatList);
  }

  filterItem(value) {
    if (!value) this.assignCopy();
    this.filteredItems = Object.assign([], this.chatList).filter(
      item => item.name.toLowerCase().indexOf(value.toLowerCase()) > -1
    )
  }

  ngOnDestroy(): void {
    this.userData.chatIds = [];
    this.ws.unsubscribe();
  }
}
