import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user/user.service";
import {UserModule} from "../../models/user/user.module";
import {WebSocketService} from "../../services/websocket/websocket.service";
import {BsDatepickerDirective} from "ngx-bootstrap";

@Component({
  selector: 'app-create-chat',
  templateUrl: './create-chat.component.html',
  styleUrls: ['./create-chat.component.sass']
})
export class CreateChatComponent implements OnInit {

  @ViewChild(BsDatepickerDirective) datepicler: BsDatepickerDirective;
  newChatForm: FormGroup;
  messageError: string = null;
  messageSuccess: string = null;
  minDate = new Date();
  itemList: UserModule[] = [];
  configDatepicker = {
    dateInputFormat: 'Do-MMMM-YYYY',
    containerClass: 'theme-blue'
  };
  settings = {
    singleSelection: false,
    idField: 'id',
    textField: 'fullName',
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    itemsShowLimit: 3,
    allowSearchFilter: true
  };
  selectedItems = [];

  constructor(private formBuilder: FormBuilder, private userService: UserService, private ws: WebSocketService) {
    this.messageError = "";
    this.messageSuccess = "";
    this.newChatForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(40), Validators.minLength(4)]],
      closeAt: ['', [Validators.required]],
      participants: ['', Validators.required]
    });
    this.userService.getParticipants().subscribe(response => {
      this.itemList = response;
    }, error => {
      this.messageError = error;
    });
  }

  ngOnInit() {
  }

  createChat() {
    let map = this.newChatForm.value.participants.map(user => user.id);
    this.newChatForm.value.participants = this.itemList.filter(user => map.includes(user.id));
    this.ws.createChat(this.newChatForm.value);
    this.newChatForm.reset();
  }

  @HostListener('window:scroll')
  onScrollEvent() {
    this.datepicler.hide();
  }

}
