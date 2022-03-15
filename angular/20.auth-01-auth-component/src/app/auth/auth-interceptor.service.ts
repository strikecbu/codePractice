import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { AuthService } from "./auth.service";
import { exhaustMap, take } from "rxjs/operators";

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return this.authService.userSubj.pipe(
      take(1),
      exhaustMap((user) => {
        if (user) {
          const newReq = req.clone({ setParams: { auth: user.token } });
          return next.handle(newReq);
        } else {
          return next.handle(req);
        }
      })
    );
  }
}
