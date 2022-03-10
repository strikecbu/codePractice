import React, { useState, useEffect, useReducer, useContext, useRef } from 'react';

import Card from '../UI/Card/Card';
import classes from './Login.module.css';
import Button from '../UI/Button/Button';
import AuthContext from '../../context/auth-context';
import Input from '../UI/Input/Input';

const emailProducer = (state, action) => {
  if (action.type === 'INPUT_VALUE') {
    return { value: action.val, isValid: action.val.includes('@') }
  }

  if (action.type === 'VALID_VALUE') {
    return { value: state.value, isValid: state.value.includes('@') }
  }
}
const passwordProducer = (state, action) => {
  if (action.type === 'INPUT_VALUE') {
    return { value: action.val, isValid: action.val.trim().length > 6 }
  }

  if (action.type === 'VALID_VALUE') {
    return { value: state.value, isValid: state.value.trim().length > 6 }
  }
}

const Login = (props) => {
  // const [enteredEmail, setEnteredEmail] = useState('');
  // const [emailIsValid, setEmailIsValid] = useState();
  // const [enteredPassword, setEnteredPassword] = useState('');
  // const [passwordIsValid, setPasswordIsValid] = useState();
  const [formIsValid, setFormIsValid] = useState(false);
  const authCtx = useContext(AuthContext);
  const emailRef = useRef();
  const passwordRef = useRef();

  const [emailState, dispatchEmail] = useReducer(emailProducer, {
    value: '',
    isValid: null
  });
  const [passwordState, dispatchPassword] = useReducer(passwordProducer, {
    value: '',
    isValid: null
  });

  useEffect(() => {
    console.log('EFFECT RUNNING');

    return () => {
      console.log('EFFECT CLEANUP');
    };
  }, []);

  useEffect(() => {
    const identifier = setTimeout(() => {
      console.log('Checking form validity!');
      setFormIsValid(
        emailState.value.includes('@') && passwordState.value.trim().length > 6
      );
    }, 500);

    return () => {
      console.log('CLEANUP');
      clearTimeout(identifier);
    };
  }, [emailState.value, passwordState.value]);

  const emailChangeHandler = (event) => {
    dispatchEmail({ type: 'INPUT_VALUE', val: event.target.value })

    // setFormIsValid(
    //   event.target.value.includes('@') && passwordState.value.trim().length > 6
    // );
  };

  const passwordChangeHandler = (event) => {
    dispatchPassword({ type: 'INPUT_VALUE', val: event.target.value });
    // passwordRef.current.value = event.target.value;

    // setFormIsValid(
    //   emailState.isValid && event.target.value.trim().length > 6
    // );
  };

  const validateEmailHandler = () => {
    dispatchEmail({ type: 'VALID_VALUE' })
    // setEmailIsValid(enteredEmail.includes('@'));
  };

  const validatePasswordHandler = () => {
    dispatchPassword({ type: 'VALID_VALUE' })
    // setPasswordIsValid(enteredPassword.trim().length > 6);
  };

  const submitHandler = (event) => {
    event.preventDefault();
    if(emailState.isValid && passwordState.isValid) {
      authCtx.onLogin(emailState.value, passwordState.value);
    } else if(!emailState.isValid) {
      emailRef.current.focus();
    } else {
      passwordRef.current.focus();
    }
  };

  return (
    <Card className={classes.login}>
      <form onSubmit={submitHandler}>
        <Input
          ref={emailRef}
          type="email"
          id="email"
          label="E-Mail"
          value={emailState.value}
          onChange={emailChangeHandler}
          onBlur={validateEmailHandler}
          isValid={emailState.isValid}
        />
        <Input
          ref={passwordRef}
          label="Password"
          type="password"
          id="password"
          onChange={passwordChangeHandler}
          onBlur={validatePasswordHandler}
          isValid={passwordState.isValid}
        />
        <div className={classes.actions}>
          <Button type="submit" className={classes.btn}>
            Login
          </Button>
        </div>
      </form>
    </Card>
  );
};

export default Login;
