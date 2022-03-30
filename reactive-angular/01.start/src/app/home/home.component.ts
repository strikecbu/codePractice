import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Course, sortCoursesBySeqNo } from '../model/course';
import { CourseService } from '../services/course.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  beginnerCourses$: Observable<Course[]>;

  advancedCourses$: Observable<Course[]>;

  constructor(private courseService: CourseService) {}

  ngOnInit() {
    const courses$ = this.courseService.getAllCourses().pipe(
      shareReplay(1),
      map((courses) => {
        return courses.sort(sortCoursesBySeqNo);
      })
    );
    this.beginnerCourses$ = courses$.pipe(
      map((courses) => {
        return courses.filter((course) => course.category === 'BEGINNER');
      })
    );
    this.advancedCourses$ = courses$.pipe(
      map((courses) => {
        return courses.filter((course) => course.category === 'ADVANCED');
      })
    );
  }
}
