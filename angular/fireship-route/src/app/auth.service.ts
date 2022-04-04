import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  private subject = new BehaviorSubject<boolean>(false);
  auth$: Observable<boolean> = this.subject.asObservable();

  setAuth() {
    this.subject.next(true);
  }

  removeAuth() {
    this.subject.next(false);
  }
}
