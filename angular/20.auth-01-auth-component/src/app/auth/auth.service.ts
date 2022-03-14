import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { catchError, tap } from "rxjs/operators";
import { Subject, throwError } from "rxjs";
import { User } from "./User.model";

export interface signApiResponse {
  idToken: string;
  email: string;
  refreshToken: string;
  expiresIn: string;
  localId: string;
  registered?: boolean;
}

@Injectable({ providedIn: "root" })
export class AuthService {
  constructor(private http: HttpClient) {}

  userSubj = new Subject<User>();

  signUp(email: string, password: string) {
    return this.http
      .post<signApiResponse>(
        "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyBJbJ5FbJpautZxK3NAQ9iutkyl7XwNtGQ",
        {
          email: email,
          password: password,
          returnSecureToken: true,
        }
      )
      .pipe(catchError(this.errorHandle), tap(this.authHandle.bind(this)));
  }

  login(email: string, password: string) {
    return this.http
      .post<signApiResponse>(
        "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBJbJ5FbJpautZxK3NAQ9iutkyl7XwNtGQ",
        {
          email: email,
          password: password,
          returnSecureToken: true,
        }
      )
      .pipe(catchError(this.errorHandle), tap(this.authHandle.bind(this)));
  }

  logout() {
    this.userSubj.next(null);
  }

  authHandle(respData: signApiResponse) {
    const expiredTime = new Date(Date.now() + +respData.expiresIn * 1000);
    const user = new User(
      respData.email,
      respData.localId,
      respData.idToken,
      expiredTime
    );
    this.userSubj.next(user);
  }

  errorHandle(errorResp) {
    let errorMessage: string;
    if (
      !errorResp.error ||
      !errorResp.error.error ||
      !errorResp.error.error.message
    ) {
      errorMessage = "Unknown error occurred!";
    }
    switch (errorResp.error.error.message) {
      case "EMAIL_EXISTS":
        errorMessage = "This email is already exist!";
        break;
      case "EMAIL_NOT_FOUND":
        errorMessage = "This email is not exist";
        break;
      case "INVALID_PASSWORD":
        errorMessage = "Password is no correct!";
        break;
    }
    return throwError(errorMessage);
  }
}
