import { Component, Input, OnInit } from '@angular/core';
import { Course } from '../model/course';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { CourseDialogComponent } from '../course-dialog/course-dialog.component';

@Component({
  selector: 'app-course-list-card',
  templateUrl: './course-list-card.component.html',
  styleUrls: ['./course-list-card.component.scss'],
})
export class CourseListCardComponent implements OnInit {
  @Input() courses: Course[];

  constructor(private dialog: MatDialog) {}

  ngOnInit(): void {}

  editCourse(course: Course) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '400px';

    dialogConfig.data = course;

    const dialogRef = this.dialog.open(CourseDialogComponent, dialogConfig);
  }
}
