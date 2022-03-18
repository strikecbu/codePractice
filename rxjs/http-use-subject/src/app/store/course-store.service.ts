import { HttpHandler } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, timer } from "rxjs";
import { fromPromise } from "rxjs/internal-compatibility";
import {
  tap,
  map,
  shareReplay,
  retryWhen,
  delayWhen,
  withLatestFrom,
  catchError,
} from "rxjs/operators";
import { createHttpObservable } from "../common/util";
import { Course } from "../model/course";

@Injectable({
  providedIn: "root",
})
export class CourseStoreService {
  private store = new BehaviorSubject<Course[]>([]);

  public courses$ = this.store.asObservable();

  getCourseData() {
    const http$ = createHttpObservable("/api/courses");

    const subs = http$
      .pipe(
        tap(() => console.log("HTTP request executed")),
        map((res) => Object.values(res["payload"])),
        retryWhen((errors) => errors.pipe(delayWhen(() => timer(2000))))
      )
      .subscribe((courses: Course[]) => {
        this.store.next(courses);
        subs.unsubscribe();
      });
  }

  getBeginnerCourses() {
    return this.getCourses("BEGINNER");
  }
  getAdvancedCourses() {
    return this.getCourses("ADVANCED");
  }

  getCourses(category: string) {
    return this.courses$.pipe(
      map((courses) => courses.filter((course) => course.category == category))
    );
  }

  updateCourse(course: Course) {
    const subs = fromPromise(
      fetch(`/api/courses/${course.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(course),
      }).then((resp) => {
        if (resp.ok) {
          return resp.json();
        } else {
          throw new Error("Fail to update data.");
        }
      })
    )
      .pipe(
        withLatestFrom(this.courses$),
        map(([resp, courses]) => {
          courses = [...courses];
          const courseIndex = courses.findIndex((item) => {
            return resp.id === item.id;
          });
          courses[courseIndex] = { ...courses[courseIndex], ...resp };
          return courses;
        })
      )
      .subscribe(
        (courses) => {
          this.store.next(courses);
          subs.unsubscribe();
        },
        (error) => {
          console.log(error);
          subs.unsubscribe();
        }
      );
  }
}
