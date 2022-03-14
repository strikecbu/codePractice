import {
  HttpEvent,
  HttpEventType,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";

@Injectable({
  providedIn: "root",
})
export class TimeLogInterceptorService implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const startTime = Date.now();
    return next.handle(req).pipe(
      tap((event) => {
        const diffTime = Date.now() - startTime;
        if (event.type === HttpEventType.Response) {
          console.log(`RequestUrl: ${req.url}`);
          console.log(`Cost time: ${diffTime} ms`);
          console.log(event.body);
        }
      })
    );
  }
}
