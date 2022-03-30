import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Course } from '../model/course';
import { CourseDialogComponent } from '../course-dialog/course-dialog.component';
import { CourseService } from '../services/course.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  beginnerCourses$: Observable<Course[]>;

  advancedCourses$: Observable<Course[]>;

  constructor(
    private dialog: MatDialog,
    private courseService: CourseService
  ) {}

  ngOnInit() {
    const courses$ = this.courseService.getAllCourses().pipe(shareReplay(1));
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

  editCourse(course: Course) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '400px';

    dialogConfig.data = course;

    const dialogRef = this.dialog.open(CourseDialogComponent, dialogConfig);
  }
}
