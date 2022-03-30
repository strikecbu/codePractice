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

@Component({
  selector: 'course-dialog',
  templateUrl: './course-dialog.component.html',
  styleUrls: ['./course-dialog.component.css'],
  providers: [LoadingService, MessagesService],
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
    private messageService: MessagesService
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
    this.loadingService.onLoading();
    const changes = this.form.value;
    const saveCourse$ = this.courseService.saveCourse(this.course.id, changes);

    this.loadingService
      .loadingUntilCompleted(saveCourse$)
      .pipe(
        retry(1),
        catchError((err) => {
          this.messageService.showErrors("Can't save course right now.");
          return throwError(err);
        })
      )
      .subscribe((value) => {
        this.dialogRef.close(true);
      });
  }

  close() {
    this.dialogRef.close();
  }
}
