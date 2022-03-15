import React, { useState } from "react";
import { catchError, Observable, of, switchMap, tap, throwError } from "rxjs";
import { fromFetch } from "rxjs/fetch";

type AuthContextObj = {
  token: string;
  isAuthentication: boolean;
  login: (email: string, password: string) => Observable<AuthResponse>;
  singUp: (email: string, password: string) => Observable<AuthResponse>;
  logout: () => void;
};

export const AuthContext = React.createContext<AuthContextObj>({
  token: "",
  isAuthentication: false,
  login: (email, password) => {
    return of();
  },
  singUp: (email, password) => {
    return of();
  },
  logout: () => {},
});

interface AuthRequest {
  email: string;
  password: string;
  returnSecureToken: boolean;
}

interface AuthResponse {
  idToken: string;
  email: string;
  refreshToken: string;
  expiresIn: string;
  localId: string;
  registered?: boolean;
}

const AuthContextProvider: React.FC = (props) => {
  const [token, setToken] = useState<string>("");

  const signUp = (
    email: string,
    password: string
  ): Observable<AuthResponse> => {
    const url =
      "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyDuRcLkLtMTqKzGoyEZjtLIjJvlmt4D4UU";
    return sendAuth(email, password, url);
  };

  const login = (email: string, password: string): Observable<AuthResponse> => {
    const url =
      "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyDuRcLkLtMTqKzGoyEZjtLIjJvlmt4D4UU";
    return sendAuth(email, password, url);
  };

  const sendAuth = (
    email: string,
    password: string,
    apiUrl: string
  ): Observable<AuthResponse> => {
    const data: AuthRequest = {
      email: email,
      password: password,
      returnSecureToken: true,
    };

    return fromFetch(apiUrl, {
      method: "POST",
      body: JSON.stringify(data),
    }).pipe(
      switchMap(async (resp) => {
        if (resp.ok) {
          return resp.json();
        } else {
          const data = await resp.json();
          throw new Error(data.error.message);
        }
      }),
      tap(data => {
        setToken(data.idToken);
      })
    );
  };

  const logout = () => {
    setToken("");
  };

  const initValue: AuthContextObj = {
    token: token,
    isAuthentication: !!token,
    login: login,
    singUp: signUp,
    logout: logout,
  };

  return (
    <AuthContext.Provider value={initValue}>
      {props.children}
    </AuthContext.Provider>
  );
};

export default AuthContextProvider;
