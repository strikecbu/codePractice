import React, { Fragment, useContext, useState } from "react";
import "./App.css";
import { NavLink, Outlet, useNavigate } from "react-router-dom";
import { AuthContext } from "./store/auth-context";

function App() {
  let [isLoginMode, setIsLoginMode] = useState(true);
  const authCtx = useContext(AuthContext);
  const navigate = useNavigate();
  let isAuthentication = authCtx.isAuthentication;

  console.log(`auth:${isAuthentication}`)
  console.log(`token:${authCtx.token}`)

  const onSwitchLoginMode = () => {
    setIsLoginMode(!isLoginMode);
  };

  const onShowAuthPage = () => {
    navigate(`/auth/${isLoginMode ? "Login" : "SignUp"}`, { replace: true });
  };

  const logoutHandler = () => {
    authCtx.logout();
    navigate("/", {replace: true})
  }
  return (
    <div className="App">
      <header>
        <h1>Bookkeeper</h1>
        <nav style={{ borderTop: "solid 1px black", padding: "1rem" }}>
          <NavLink
            style={({ isActive }) => {
              return {
                margin: "1rem 0",
                color: isActive ? "red" : "",
              };
            }}
            to="/expenses"
          >
            Expenses
          </NavLink>
          {authCtx.isAuthentication && (
            <Fragment>
              {" "}
              |{" "}
              <NavLink
                style={({ isActive }) => {
                  return {
                    margin: "1rem 0",
                    color: isActive ? "red" : "",
                  };
                }}
                to="/invoices"
              >
                Invoices
              </NavLink>
            </Fragment>
          )}
        </nav>
        <div>
          {!isAuthentication && (
            <Fragment>
              <button onClick={onShowAuthPage}>
                {isLoginMode ? "Login" : "SignUp"}
              </button>
              <button onClick={onSwitchLoginMode}>Switch Login</button>
            </Fragment>
          )}
          {isAuthentication && <button onClick={logoutHandler}>Logout</button>}
        </div>
      </header>
      <div>
        <Outlet />
      </div>
    </div>
  );
}

export default App;
