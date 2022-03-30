import { AfterViewInit, Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Course } from '../model/course';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
import { CourseService } from '../services/course.service';
import { catchError, map, retry, retryWhen, take } from 'rxjs/operators';
import { LoadingService } from '../loading/loading.service';
import { MessagesService } from '../messages/messages.service';
import { throwError, timer } from 'rxjs';
import { CourseStore } from '../services/course.store';

@Component({
  selector: 'course-dialog',
  templateUrl: './course-dialog.component.html',
  styleUrls: ['./course-dialog.component.css'],
})
export class CourseDialogComponent implements AfterViewInit {
  form: FormGroup;

  course: Course;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<CourseDialogComponent>,
    @Inject(MAT_DIALOG_DATA) course: Course,
    private courseService: CourseService,
    private loadingService: LoadingService,
    private messageService: MessagesService,
    private courseStore: CourseStore
  ) {
    this.course = course;

    this.form = fb.group({
      description: [course.description, Validators.required],
      category: [course.category, Validators.required],
      releasedAt: [moment(), Validators.required],
      longDescription: [course.longDescription, Validators.required],
    });
  }

  ngAfterViewInit() {}

  save() {
    const changes = this.form.value;
    this.courseStore.saveCourse(this.course.id, changes).subscribe();
    this.dialogRef.close(true);
  }

  close() {
    this.dialogRef.close();
  }
}
