import { createSlice } from "@reduxjs/toolkit";

const calcTimeExpires = (expiresSecond) => {
  const nowTime = new Date().getTime();
  const expiresTime = nowTime + expiresSecond * 1000;
  return expiresTime;
};

const calcTimeLeft = (expiresTime) => {
  const nowTime = new Date().getTime();
  console.log(nowTime);
  return nowTime > expiresTime ? 0 : expiresTime - nowTime;
};

let logoutTimer;

export const login = (token, expiresSecond) => {
  return (dispatch) => {
    const expiresTime = calcTimeExpires(expiresSecond);
    clearTimeout(logoutTimer);
    dispatch(authActions.login(token));

    localStorage.setItem("token", token);
    localStorage.setItem("expiresTime", expiresTime);
    logoutTimer = setTimeout(() => {
      dispatch(logout());
    }, calcTimeLeft(expiresTime));
  };
};

export const logout = () => {
  return (dispatch) => {
    dispatch(authActions.logout());
    localStorage.removeItem("token");
    localStorage.removeItem("expiresTime");
    clearTimeout(logoutTimer);
  };
};

export const checkAuth = () => {
  return (dispatch) => {
    const token = localStorage.getItem("token");
    const expiresTime = localStorage.getItem("expiresTime");
    const leftTime = calcTimeLeft(expiresTime);
    if (leftTime > 0) {
      dispatch(authActions.login(token));
      logoutTimer = setTimeout(() => {
        logout();
      }, leftTime);
    } else {
      logout();
    }
  };
};

const authSlice = createSlice({
  name: "auth",
  initialState: {
    token: "",
    isLogin: false,
  },
  reducers: {
    login(state, action) {
      state.token = action.payload;
      state.isLogin = !!action.payload;
    },
    logout(state, action) {
      state.token = null;
      state.isLogin = false;
    },
  },
});

export const authActions = authSlice.actions;
export const authReducer = authSlice.reducer;
