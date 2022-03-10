import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  evenNum = 0;
  oddNum = 0;
  gameStart = false;

  numberAdd(value) {
    if (value === 0) {
      this.reset();
      return;
    }
    if (value % 2 === 0) {
      this.evenNum = value;
    } else {
      this.oddNum = value;
    }
    this.gameStart = true;

  }

  reset() {
    this.oddNum = 0;
    this.evenNum = 0;
    this.gameStart = false;
  }


}
