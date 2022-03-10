import { Switch, Route } from "react-router-dom";
import { useContext, useEffect } from "react";

import Layout from "./components/Layout/Layout";
import UserProfile from "./components/Profile/UserProfile";
import AuthPage from "./pages/AuthPage";
import HomePage from "./pages/HomePage";
import AuthContext from "./store/auth-context";
import { Redirect } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { checkAuth } from "./store/auth-slice";

function App() {
  const authCtx = useContext(AuthContext);
  const isLogin = useSelector(state => state.isLogin);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(checkAuth());
  }, [dispatch]);

  return (
    <Layout>
      <Switch>
        <Route path="/" exact>
          <HomePage />
        </Route>
        {!isLogin && (
          <Route path="/auth">
            <AuthPage />
          </Route>
        )}

        <Route path="/profile">
          {isLogin && <UserProfile />}
          {!isLogin && <Redirect to="/auth" />}
        </Route>
        <Route path="*">
          <Redirect to="/" />
        </Route>
      </Switch>
    </Layout>
  );
}

export default App;
