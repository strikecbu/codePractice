import { User } from "../user.model";
import * as AuthActions from "./auth.actions";

export interface State {
  user: User;
}

const initState: State = {
  user: null,
};

export function authReducer(
  state = initState,
  action: AuthActions.authActions
): State {
  switch (action.type) {
    case AuthActions.LOGIN:
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
      };
    case AuthActions.LOGOUT:
      return {
        ...state,
        user: null,
      };
    default:
      return state;
  }
}
