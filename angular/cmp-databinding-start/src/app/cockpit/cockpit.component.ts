import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-cockpit',
  templateUrl: './cockpit.component.html',
  styleUrls: ['./cockpit.component.css']
})
export class CockpitComponent implements OnInit {
  newServerName = '';
  newServerContent = '';
  @Output() serverEvent = new EventEmitter<{
    type: string,
    name: string,
    content: string
  }>();

  constructor() {
  }

  ngOnInit(): void {
  }

  onAddServer(type: string) {
    this.serverEvent.emit({
      type: type,
      name: this.newServerName,
      content: this.newServerContent
    });
  }


}
