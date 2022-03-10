import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {

  @Output() selectEvent = new EventEmitter<string>();

  onSelect(feature: string) {
    this.selectEvent.emit(feature);
  }
}
