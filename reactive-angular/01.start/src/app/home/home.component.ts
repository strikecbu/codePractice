import { Component, OnInit } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Course, sortCoursesBySeqNo } from '../model/course';
import { CourseService } from '../services/course.service';
import { LoadingService } from '../loading/loading.service';
import { MessagesService } from '../messages/messages.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  beginnerCourses$: Observable<Course[]>;

  advancedCourses$: Observable<Course[]>;

  constructor(
    private courseService: CourseService,
    private loadingService: LoadingService,
    private messageService: MessagesService
  ) {}

  ngOnInit() {
    this.reloadCourses();
  }

  reloadCourses() {
    const courses$ = this.courseService.getAllCourses().pipe(
      map((courses) => {
        return courses.sort(sortCoursesBySeqNo);
      }),
      catchError((err) => {
        this.messageService.showErrors(
          'loading courses fail.',
          'Run for your life'
        );
        console.log(err);
        return throwError(err);
      })
    );
    const loadingCourses$ = this.loadingService.loadingUntilCompleted(courses$);

    this.beginnerCourses$ = loadingCourses$.pipe(
      map((courses) => {
        return courses.filter((course) => course.category === 'BEGINNER');
      })
    );
    this.advancedCourses$ = loadingCourses$.pipe(
      map((courses) => {
        return courses.filter((course) => course.category === 'ADVANCED');
      })
    );
  }
}
