import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Course } from '../model/course';

@Injectable({ providedIn: 'root' })
export class CourseService {
  constructor(private http: HttpClient) {}

  getAllCourses(): Observable<Course[]> {
    return this.http.get('/api/courses').pipe(
      map((resp) => {
        return resp['payload'];
      })
    );
  }

  saveCourse(courseId: string, value: Partial<Course>): Observable<any> {
    return this.http.put(`/api/courses/${courseId}`, value).pipe(shareReplay());
  }
}
