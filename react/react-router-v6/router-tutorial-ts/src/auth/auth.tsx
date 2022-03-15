import React, { FormEvent, useContext, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Observable } from "rxjs";
import { AuthContext } from "../store/auth-context";
import { AuthResponse, login, signUp } from "./auth.service";

const Auth: React.FC = () => {
  const params = useParams();
  const navigate = useNavigate();
  const authCtx = useContext(AuthContext);
  const mode = params.mode;

  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  
  const [error, setError] = useState(null);

  
  const onSubmitHandler = (event: FormEvent) => {
    event.preventDefault();
    const email = emailRef.current?.value;
    const password = passwordRef.current?.value;
    let subs: Observable<AuthResponse>;
    if(!email || !password) {
        return
    }
    
    if('SignUp' === mode) {
        subs = authCtx.singUp(email, password);
    } else {
        subs = authCtx.login(email, password);
    }

    const subsc = subs.subscribe({
        next: (data) => {
            console.log(data);
            subsc.unsubscribe();
            navigate("/", {replace: true});
        },
        error: (error) => {
            console.log(error);
            setError(error.toString());
            subsc.unsubscribe();
        }
    })
  }



  return (
    <main>
      <h2>{mode} User Information</h2>
      <p style={{color: 'red'}}>{error}</p>
      <form onSubmit={onSubmitHandler}>
        <div>
          <label htmlFor="email">
            Email:
            <input type="email" id="email" ref={emailRef} />
          </label>
        </div>
        <div>
          <label htmlFor="password">
            Password:
            <input type="password" id="password" ref={passwordRef} />
          </label>
        </div>
        <button type="submit" >Submit</button>
      </form>
    </main>
  );
};

export default Auth;
