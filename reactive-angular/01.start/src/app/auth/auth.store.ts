import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../model/user';
import { map, tap } from 'rxjs/operators';

const AUTH_USER = 'AUTH_USER';

@Injectable({ providedIn: 'root' })
export class AuthStore {
  constructor(private http: HttpClient) {
    const user = localStorage.getItem(AUTH_USER);
    if (user) {
      this.subject.next(JSON.parse(user));
    }
  }

  private subject = new BehaviorSubject<User>(null);
  user$: Observable<User> = this.subject.asObservable();
  isLogin$: Observable<boolean> = this.user$.pipe(map((user) => !!user));

  login(email: string, password: string) {
    return this.http
      .post<User>('/api/login', {
        email,
        password,
      })
      .pipe(
        tap((user) => this.subject.next(user)),
        tap((user) => {
          localStorage.setItem(AUTH_USER, JSON.stringify(user));
        })
      );
  }

  logout() {
    this.subject.next(null);
    localStorage.removeItem(AUTH_USER);
  }
}
