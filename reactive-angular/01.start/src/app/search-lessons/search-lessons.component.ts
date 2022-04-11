import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../model/course';
import {
  debounceTime,
  distinctUntilChanged,
  startWith,
  tap,
  delay,
  map,
  concatMap,
  switchMap,
  withLatestFrom,
  concatAll,
  shareReplay,
} from 'rxjs/operators';
import { merge, fromEvent, Observable, concat } from 'rxjs';
import { Lesson } from '../model/lesson';
import { CourseService } from '../services/course.service';

@Component({
  selector: 'course',
  templateUrl: './search-lessons.component.html',
  styleUrls: ['./search-lessons.component.css'],
})
export class SearchLessonsComponent implements OnInit {
  constructor(private courseService: CourseService) {}

  lessons$: Observable<Lesson[]>;

  showDetail = false;
  lesson: Lesson;

  ngOnInit() {}

  onSearch(value: string) {
    this.backSearch();
    this.lessons$ = this.courseService.searchLesson(value);
  }

  showLessonDetail(lesson: Lesson) {
    console.log('show lesson');
    this.showDetail = true;
    this.lesson = lesson;
  }

  backSearch() {
    this.showDetail = false;
    this.lesson = null;
  }
}
