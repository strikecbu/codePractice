import { createContext, useCallback, useState } from 'react';

export const AuthContext = createContext({
  isLogin: false,
  login: () => {},
  logout: () => {},
});

export const useAuthCtxInitValue = () => {
  const [isLogin, setLogin] = useState(false);
  const login = useCallback(() => {
    console.log(isLogin);
    setLogin(true);
  }, [isLogin]);

  const logout = useCallback(() => {
    setLogin(false);
  }, []);

  return {
    isLogin,
    login,
    logout,
  };
};
