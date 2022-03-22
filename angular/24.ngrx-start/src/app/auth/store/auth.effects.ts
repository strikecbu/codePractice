import { Actions, createEffect, ofType } from "@ngrx/effects";
import { Injectable } from "@angular/core";
import { catchError, map, switchMap, tap } from "rxjs/operators";
import { HttpClient } from "@angular/common/http";

import * as AuthActions from "./auth.actions";
import { of } from "rxjs";
import { Router } from "@angular/router";
import { User } from "../user.model";
import { AuthService } from "../auth.service";
import {AuthenticationSuccess} from "./auth.actions";

export interface AuthResponseData {
  kind: string;
  idToken: string;
  email: string;
  refreshToken: string;
  expiresIn: string;
  localId: string;
  registered?: boolean;
}

@Injectable()
export class AuthEffects {
  loginEffect$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthActions.START_LOGIN),
      switchMap((actionData: AuthActions.StartLogin) => {
        console.log("login effect");
        return this.http
          .post<AuthResponseData>(
            "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBJbJ5FbJpautZxK3NAQ9iutkyl7XwNtGQ",
            {
              email: actionData.payload.email,
              password: actionData.payload.password,
              returnSecureToken: true,
            }
          )
          .pipe(
            tap((respData) => {
              this.authService.setLogoutTimer(+respData.expiresIn * 1000);
            }),
            map((respData) => {
              return this.handleAuthentication(
                respData.email,
                respData.localId,
                respData.idToken,
                +respData.expiresIn
              );
            }),
            catchError((error) => {
              return of(this.handleAuthenticationError(error));
            })
          );
      })
    );
  });

  signupEffect$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthActions.START_SIGNUP),
      switchMap((actionData: AuthActions.StartSignup) => {
        return this.http
          .post<AuthResponseData>(
            "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyBJbJ5FbJpautZxK3NAQ9iutkyl7XwNtGQ",
            {
              email: actionData.payload.email,
              password: actionData.payload.password,
              returnSecureToken: true,
            }
          )
          .pipe(
            tap((respData) => {
              this.authService.setLogoutTimer(+respData.expiresIn * 1000);
            }),
            map((respData) => {
              return this.handleAuthentication(
                respData.email,
                respData.localId,
                respData.idToken,
                +respData.expiresIn
              );
            }),
            catchError((error) => {
              return of(this.handleAuthenticationError(error));
            })
          );
      })
    );
  });

  loginSuccess$ = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(AuthActions.AUTHENTICATION_SUCCESS),
        tap((action: AuthenticationSuccess) => {
          if (action.payload.isRedirect) {
            this.router.navigate(["/"]);
          }
        })
      );
    },
    { dispatch: false }
  );

  logoutEffect$ = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(AuthActions.LOGOUT),
        tap(() => {
          localStorage.removeItem("userData");
          this.authService.clearLogoutTimer();
        }),
        tap(() => {
          this.router.navigate(["/auth"]);
        })
      );
    },
    { dispatch: false }
  );

  autoLoginEffect$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthActions.AUTO_LOGIN),
      map((action: AuthActions.AutoLogin) => {
        const userData: {
          email: string;
          id: string;
          _token: string;
          _tokenExpirationDate: string;
        } = JSON.parse(localStorage.getItem("userData"));
        if (!userData) {
          return { type: "NOT_EXIST" };
        }

        if (userData._token) {
          // this.user.next(loadedUser);
          const expirationDuration =
            new Date(userData._tokenExpirationDate).getTime() -
            new Date().getTime();
          this.authService.setLogoutTimer(expirationDuration);

          return new AuthActions.AuthenticationSuccess({
            email: userData.email,
            userId: userData.id,
            token: userData._token,
            expirationDate: new Date(userData._tokenExpirationDate),
            isRedirect: false
          });
        }
        return { type: "NOT_EXIST" };
      })
    );
  });


  handleAuthentication = (
    email: string,
    userId: string,
    token: string,
    expiresIn: number
  ) => {
    const expirationDate = new Date(new Date().getTime() + expiresIn * 1000);
    const user = new User(email, userId, token, expirationDate);
    localStorage.setItem("userData", JSON.stringify(user));
    return new AuthActions.AuthenticationSuccess({
      email,
      userId,
      token,
      expirationDate: expirationDate,
      isRedirect: true
    });
  };

  handleAuthenticationError = (errorRes) => {
    let errorMessage = "An unknown error occurred!";
    if (!errorRes.error || !errorRes.error.error) {
      return new AuthActions.AuthenticationError(errorMessage);
    }
    switch (errorRes.error.error.message) {
      case "EMAIL_EXISTS":
        errorMessage = "This email exists already";
        break;
      case "EMAIL_NOT_FOUND":
        errorMessage = "This email does not exist.";
        break;
      case "INVALID_PASSWORD":
        errorMessage = "This password is not correct.";
        break;
    }
    return new AuthActions.AuthenticationError(errorMessage);
  };

  constructor(
    private actions$: Actions,
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}
}
