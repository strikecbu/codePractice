import { Component } from '@angular/core';
import {
  animate,
  group,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  animations: [
    trigger('divState', [
      state(
        'normal',
        style({
          'background-color': 'red',
          transform: 'translateX(0px)',
        })
      ),
      state(
        'goAni',
        style({
          'background-color': 'blue',
          transform: 'translateX(100px)',
        })
      ),
      transition('normal <=> goAni', [animate(1000)]),
    ]),
    trigger('wildState', [
      state(
        'normal',
        style({
          'background-color': 'red',
          transform: 'translateX(0px) scale(1)',
        })
      ),
      state(
        'goAni',
        style({
          'background-color': 'blue',
          transform: 'translateX(100px) scale(1)',
        })
      ),
      state(
        'goWild',
        style({
          'background-color': 'green',
          transform: 'scale(0.5)',
        })
      ),
      transition('normal <=> goAni', [animate(700)]),
      transition('goWild <=> *', animate(500)),
    ]),
    trigger('list1', [
      transition('void => *', [
        style({
          opacity: '0',
          transform: 'translateY(-50px)',
        }),
        animate(800),
      ]),
      transition('* => void', [
        group([
          animate(200, style({ color: 'red' })),
          animate(
            700,
            style({
              opacity: '0',
              transform: 'translateY(50px)',
            })
          ),
        ]),
      ]),
    ]),
  ],
})
export class AppComponent {
  list = ['Milk', 'Sugar', 'Bread'];
  state = 'normal';
  wildState = 'normal';

  onAdd(item) {
    this.list.push(item);
  }

  onAnimate($event: MouseEvent) {
    this.state = this.state === 'normal' ? 'goAni' : 'normal';
    this.wildState = this.wildState === 'normal' ? 'goAni' : 'normal';
    console.log($event);
  }

  onWild() {
    this.wildState = 'goWild';
  }

  onDelete(item: string) {
    this.list = this.list.filter((it) => it !== item);
  }
}
