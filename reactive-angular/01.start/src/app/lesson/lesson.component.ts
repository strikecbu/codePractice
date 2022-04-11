import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Lesson } from '../model/lesson';

@Component({
  selector: 'lesson',
  templateUrl: './lesson.component.html',
  styleUrls: ['./lesson.component.css'],
})
export class LessonComponent {
  @Input() lesson: Lesson;

  @Output() backSearchEvent = new EventEmitter();

  backSearch() {
    this.backSearchEvent.emit();
  }
}
