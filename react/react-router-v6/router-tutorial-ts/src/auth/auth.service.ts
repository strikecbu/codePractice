import { Observable } from "rxjs";
import { fromFetch } from "rxjs/fetch";

interface AuthRequest {
  email: string;
  password: string;
  returnSecureToken: boolean;
}

export interface AuthResponse {
  idToken: string;
  email: string;
  refreshToken: string;
  expiresIn: string;
  localId: string;
  registered?: boolean;
}

export function signUp(
  email: string,
  password: string
): Observable<AuthResponse> {
  const data: AuthRequest = {
    email: email,
    password: password,
    returnSecureToken: true,
  };
  return fromFetch<AuthResponse>(
    "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyDuRcLkLtMTqKzGoyEZjtLIjJvlmt4D4UU",
    {
      method: "POST",
      body: JSON.stringify(data),
      selector: (resp) => resp.json(),
    }
  );
}

export function login(email: string, password: string) {
  const data: AuthRequest = {
    email: email,
    password: password,
    returnSecureToken: true,
  };
  return fromFetch<AuthResponse>(
    "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyDuRcLkLtMTqKzGoyEZjtLIjJvlmt4D4UU",
    {
      method: "POST",
      body: JSON.stringify(data),
      selector: (resp) => resp.json(),
    }
  );
}
