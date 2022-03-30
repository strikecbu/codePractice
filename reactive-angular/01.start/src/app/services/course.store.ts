import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { Course, sortCoursesBySeqNo } from '../model/course';
import { MessagesService } from '../messages/messages.service';
import { catchError, map, shareReplay, tap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { LoadingService } from '../loading/loading.service';

@Injectable({ providedIn: 'root' })
export class CourseStore {
  private subject = new BehaviorSubject<Course[]>([]);
  courses$ = this.subject.asObservable();

  constructor(
    private http: HttpClient,
    private loadingService: LoadingService,
    private messageService: MessagesService
  ) {
    this.loadCourses();
  }

  private loadCourses() {
    const courses$ = this.http.get<Course[]>('/api/courses').pipe(
      shareReplay(1),
      map((resp) => {
        return resp['payload'];
      }),
      tap((courses) => this.subject.next(courses)),
      catchError((err) => {
        this.messageService.showErrors('Loading courses fail');
        return throwError(err);
      })
    );
    return this.loadingService.loadingUntilCompleted(courses$).subscribe();
  }

  filterByCategory(category: string): Observable<Course[]> {
    return this.courses$.pipe(
      map((courses) =>
        courses
          .filter((course) => course.category === category)
          .sort(sortCoursesBySeqNo)
      )
    );
  }

  saveCourse(courseId: string, payload: Partial<Course>): Observable<any> {
    const courses = this.subject.getValue();
    const index = courses.findIndex((course) => course.id === courseId);
    const newCourse = {
      ...courses[index],
      ...payload,
    };
    const newCourses = courses.slice(0);
    newCourses[index] = newCourse;

    const saveHttp$ = this.http.put(`/api/courses/${courseId}`, payload).pipe(
      shareReplay(),
      tap(() => {
        this.subject.next(newCourses);
      }),
      catchError((err) => {
        this.messageService.showErrors('Save course is fail');
        return throwError(err);
      })
    );
    return this.loadingService.loadingUntilCompleted(saveHttp$);
  }
}
