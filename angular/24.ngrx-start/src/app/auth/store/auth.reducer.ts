import { User } from "../user.model";
import * as AuthActions from "./auth.actions";

export interface State {
  user: User;
  isLoading: boolean;
  errorMsg: string;
}

const initState: State = {
  user: null,
  isLoading: false,
  errorMsg: null,
};

export function authReducer(
  state = initState,
  action: AuthActions.authActions
): State {
  switch (action.type) {
    case AuthActions.AUTHENTICATION_SUCCESS:
      const payload = action.payload;
      const user = new User(
        payload.email,
        payload.userId,
        payload.token,
        payload.expirationDate
      );
      return {
        ...state,
        user,
        isLoading: false,
      };
    case AuthActions.LOGOUT:
      return {
        ...state,
        user: null,
      };
    case AuthActions.AUTHENTICATION_ERROR:
      return {
        ...state,
        errorMsg: action.payload,
        isLoading: false,
      };
    case AuthActions.START_LOGIN:
    case AuthActions.START_SIGNUP:
      console.log("login reducer");
      return {
        ...state,
        isLoading: true,
      };
    case AuthActions.CLEAR_ERROR:
      return {
        ...state,
        errorMsg: null,
      };
    default:
      return state;
  }
}
