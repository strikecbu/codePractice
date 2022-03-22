import { Action } from "@ngrx/store";

export const AUTHENTICATION_SUCCESS = "[auth] AUTHENTICATION_SUCCESS";
export const LOGOUT = "[auth] LOGOUT";

export const AUTHENTICATION_ERROR = "[auth] AUTHENTICATION_ERROR";
export const CLEAR_ERROR = "[auth] CLEAR_ERROR";
export const START_LOGIN = "[auth] START_LOGIN";
export const AUTO_LOGIN = "[auth] AUTO_LOGIN";
export const START_SIGNUP = "[auth] START_SIGNUP";

export class AuthenticationSuccess implements Action {
  readonly type = AUTHENTICATION_SUCCESS;

  constructor(
    public payload: {
      email: string;
      userId: string;
      token: string;
      expirationDate: Date;
      isRedirect: boolean
    }
  ) {}
}

export class Logout implements Action {
  readonly type = LOGOUT;

  constructor() {}
}

export class AuthenticationError implements Action {
  readonly type = AUTHENTICATION_ERROR;

  constructor(public payload: string) {}
}

export class StartLogin implements Action {
  readonly type = START_LOGIN;

  constructor(public payload: { email: string; password: string }) {}
}

export class StartSignup implements Action {
  readonly type = START_SIGNUP;

  constructor(public payload: { email: string; password: string }) {}
}

export class ClearError implements Action {
  readonly type = CLEAR_ERROR;
}

export class AutoLogin implements Action {
  readonly type = AUTO_LOGIN;
}

export type authActions =
  | AuthenticationSuccess
  | Logout
  | StartLogin
  | StartSignup
  | AuthenticationError
  | AutoLogin
  | ClearError;
