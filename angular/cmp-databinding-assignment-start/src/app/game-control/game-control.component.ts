import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-game-control',
  templateUrl: './game-control.component.html',
  styleUrls: ['./game-control.component.css']
})
export class GameControlComponent implements OnInit {

  showNum = 0;
  timer: number;
  gameStart = false;

  @Output() numberEvent = new EventEmitter<number>();

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnDestory() {
    clearInterval(this.timer);
  }

  startGameHandler() {
    if (!this.gameStart) {
      this.gameStart = true;
      console.log('Start game...');
      this.timer = setInterval(() => {
        this.showNum++;
        this.numberEvent.emit(this.showNum);
      }, 1000);
    }
  }

  stopGameHandler() {
    clearInterval(this.timer);
    console.log('Game canceled');
    this.gameStart = false;
    this.timer = null;
    this.showNum = 0;
    this.numberEvent.emit(this.showNum);
  }

}
