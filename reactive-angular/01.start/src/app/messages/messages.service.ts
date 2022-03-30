import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { skip } from 'rxjs/operators';

@Injectable()
export class MessagesService {
  private subject = new BehaviorSubject<string[]>([]);
  errors$: Observable<string[]> = this.subject.asObservable().pipe(skip(1));

  showErrors(...errors: string[]) {
    this.subject.next(errors);
  }
}
