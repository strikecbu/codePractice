import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Course, sortCoursesBySeqNo } from '../model/course';
import { CourseService } from '../services/course.service';
import { LoadingService } from '../loading/loading.service';

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
    private loadingService: LoadingService
  ) {}

  ngOnInit() {
    this.reloadCourses();
  }

  reloadCourses() {
    const courses$ = this.courseService.getAllCourses().pipe(
      map((courses) => {
        return courses.sort(sortCoursesBySeqNo);
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
