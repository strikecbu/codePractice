import { Link, useHistory} from "react-router-dom";
import { useContext } from "react";
import classes from "./MainNavigation.module.css";
import AuthContext from "../../store/auth-context";
import { useDispatch, useSelector } from "react-redux";
import { logout as authLogoutHandler } from "../../store/auth-slice";

const MainNavigation = () => {
  const authCtx = useContext(AuthContext);
  const dispatch = useDispatch();
  const isLogin = useSelector(state => state.isLogin);
  const history = useHistory();
  // const isLogin = authCtx.isLogin;

  const logoutHandler = () => {
    dispatch(authLogoutHandler());
    history.replace('/auth');
  }
  return (
    <header className={classes.header}>
      <Link to="/">
        <div className={classes.logo}>React Auth</div>
      </Link>
      <nav>
        <ul>
          {!isLogin && (
            <li>
              <Link to="/auth">Login</Link>
            </li>
          )}

          {isLogin && (
            <li>
              <Link to="/profile">Profile</Link>
            </li>
          )}

          {isLogin && (
            <li>
              <button onClick={logoutHandler}>Logout</button>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default MainNavigation;
