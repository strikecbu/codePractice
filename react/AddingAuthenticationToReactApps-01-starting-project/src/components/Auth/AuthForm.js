import { useState, useRef, useContext } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import AuthContext, { apiKey } from "../../store/auth-context";
import { login, logout } from "../../store/auth-slice";


import classes from "./AuthForm.module.css";

const AuthForm = () => {
  // const authCtx = useContext(AuthContext);
  const isUserLogin = useSelector(state => state.isLogin);
  const dispatch = useDispatch();
  const history = useHistory();
  const [isLogin, setIsLogin] = useState(true);
  const emailRef = useRef();
  const passwordRef = useRef();

  const switchAuthModeHandler = () => {
    setIsLogin((prevState) => !prevState);
  };

  const submitHandler = async (event) => {
    event.preventDefault();
    const email = emailRef.current.value;
    const password = passwordRef.current.value;

    let url;
    if (isLogin) {
      url = `https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${apiKey}`;
    } else {
      url = `https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=${apiKey}`;
    }
    await fetch(url, {
      method: "POST",
      headers: new Headers({
        "Content-Type": "application/json",
      }),
      body: JSON.stringify({
        email,
        password,
        returnSecureToken: true,
      }),
    })
      .then(async (response) => {
        dispatch(logout());
        if (response.ok) {
          return response.json();
        }

        const json = await response.json();
        let errorMessage = "auth fail!";
        if (json.error && json.error.message) {
          errorMessage = json.error.message;
        }
        throw new Error(errorMessage);
      })
      .then((json) => {
        // storeValue.token = json.idToken;
        // storeValue.isLogin = true;
        dispatch(login(json.idToken, json.expiresIn))
        // authCtx.login(json.idToken, json.expiresIn);
        console.log("isLogin:" + isUserLogin);
        console.log(json);
        history.replace("/");
      })
      .catch((error) => {
        alert(error.message);
      });
  };

  return (
    <section className={classes.auth}>
      <h1>{isLogin ? "Login" : "Sign Up"}</h1>
      <form onSubmit={submitHandler}>
        <div className={classes.control}>
          <label htmlFor="email">Your Email</label>
          <input type="email" id="email" required ref={emailRef} />
        </div>
        <div className={classes.control}>
          <label htmlFor="password">Your Password</label>
          <input type="password" id="password" required ref={passwordRef} />
        </div>
        <div className={classes.actions}>
          <button type="submit">{isLogin ? "Login" : "Create Account"}</button>
          <button
            type="button"
            className={classes.toggle}
            onClick={switchAuthModeHandler}
          >
            {isLogin ? "Create new account" : "Login with existing account"}
          </button>
        </div>
      </form>
    </section>
  );
};

export default AuthForm;
