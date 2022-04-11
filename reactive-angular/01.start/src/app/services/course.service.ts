import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Course } from '../model/course';
import { Lesson } from '../model/lesson';

@Injectable({ providedIn: 'root' })
export class CourseService {
  constructor(private http: HttpClient) {}

  getAllCourses(): Observable<Course[]> {
    return this.http.get('/api/courses').pipe(
      shareReplay(1),
      map((resp) => {
        return resp['payload'];
      })
    );
  }

  searchLesson(search: string): Observable<Lesson[]> {
    return this.http
      .get<{ payload: Lesson[] }>('/api/lessons', {
        params: {
          filter: search,
          pageSize: 100,
        },
      })
      .pipe(
        map((data) => data.payload),
        shareReplay()
      );
  }

  saveCourse(courseId: string, value: Partial<Course>): Observable<any> {
    return this.http.put(`/api/courses/${courseId}`, value).pipe(shareReplay());
  }
}
