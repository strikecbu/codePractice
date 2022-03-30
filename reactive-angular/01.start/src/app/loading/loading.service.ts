import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { concatMap, finalize, tap } from 'rxjs/operators';

@Injectable()
export class LoadingService {
  private loadingSubject = new BehaviorSubject<boolean>(false);
  loadingObs$: Observable<boolean> = this.loadingSubject.asObservable();

  loadingUntilCompleted<T>(obs$: Observable<T>): Observable<T> {
    return of(null).pipe(
      tap(() => this.onLoading()),
      concatMap(() => obs$),
      finalize(() => this.offLoading())
    );
  }

  onLoading() {
    this.loadingSubject.next(true);
  }

  offLoading() {
    this.loadingSubject.next(false);
  }
}
