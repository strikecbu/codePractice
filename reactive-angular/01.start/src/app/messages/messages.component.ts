import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { MessagesService } from './messages.service';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css'],
})
export class MessagesComponent implements OnInit {
  showError = false;
  error$: Observable<string[]>;

  constructor(public messagesService: MessagesService) {}

  ngOnInit() {
    this.error$ = this.messagesService.errors$.pipe(
      tap(() => {
        this.showError = true;
      })
    );
  }

  onClose() {
    this.showError = false;
  }
}
