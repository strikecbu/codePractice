import classes from "./ProfileForm.module.css";
import { useRef, useContext } from "react";
import AuthContext, { apiKey } from "../../store/auth-context";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../../store/auth-slice";

const ProfileForm = () => {
  const passwordRef = useRef();
  const authCtx = useContext(AuthContext);
  const dispatch = useDispatch();
  const token = useSelector((state) => state.token);

  const submitHandler = (event) => {
    event.preventDefault();
    const password = passwordRef.current.value;
    fetch(
      `https://identitytoolkit.googleapis.com/v1/accounts:update?key=${apiKey}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          idToken: token,
          password: password,
          returnSecureToke: true,
        }),
      }
    ).then((response) => {
      if (response.ok) {
        dispatch(logout());
        // authCtx.logout();
      }
    });
  };
  return (
    <form className={classes.form} onSubmit={submitHandler}>
      <div className={classes.control}>
        <label htmlFor="new-password">New Password</label>
        <input type="password" id="new-password" ref={passwordRef} />
      </div>
      <div className={classes.action}>
        <button type="submit">Change Password</button>
      </div>
    </form>
  );
};

export default ProfileForm;
