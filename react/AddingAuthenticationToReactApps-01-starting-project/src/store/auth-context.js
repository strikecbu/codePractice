import React, { useState, useEffect, useCallback } from "react";

const AuthContext = React.createContext({
  token: "",
  isLogin: false,
  login: (token, expiresSecond) => {},
  logout: () => {},
});

export default AuthContext;

const calcTimeExpires = (expiresSecond) => {
  const nowTime = new Date().getTime();
  const expiresTime = nowTime + expiresSecond * 1000;
  return expiresTime;
};

const calcTimeLeft = (expiresTime) => {
  const nowTime = new Date().getTime();
  console.log(nowTime)
  return nowTime > expiresTime ? 0 : expiresTime - nowTime;
};

let logoutTimer;
export const AuthProvider = (props) => {
  const [token, setToken] = useState("");
  const isLogin = !!token;

  const loginHandler = (token, expiresSecond) => {
    const expiresTime = calcTimeExpires(expiresSecond);
    clearTimeout(logoutTimer);
    setToken(token);

    localStorage.setItem("token", token);
    localStorage.setItem("expiresTime", expiresTime);
    logoutTimer = setTimeout(() => {
      logoutHandler();
    }, calcTimeLeft(expiresTime));
  };

  const logoutHandler = useCallback(() => {
    setToken(null);
    localStorage.removeItem("token");
    localStorage.removeItem("expiresTime");
    clearTimeout(logoutTimer);
  }, []);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const expiresTime = localStorage.getItem("expiresTime");
    const leftTime = calcTimeLeft(expiresTime);
    if (leftTime > 0) {
      setToken(token);
      logoutTimer = setTimeout(() => {
        logoutHandler();
      }, leftTime);
    } else {
      logoutHandler();
    }
  }, [logoutHandler]);

  const initValue = {
    token: token,
    isLogin: isLogin,
    login: loginHandler,
    logout: logoutHandler,
  };
  return (
    <AuthContext.Provider value={initValue}>
      {props.children}
    </AuthContext.Provider>
  );
};

export const apiKey = "AIzaSyDuRcLkLtMTqKzGoyEZjtLIjJvlmt4D4UU";
