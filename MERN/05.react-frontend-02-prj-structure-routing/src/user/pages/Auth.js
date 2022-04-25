import React, { useContext, useState } from 'react';
import Input from '../../shared/components/FormElements/Input';
import {
  VALIDATOR_EMAIL,
  VALIDATOR_MINLENGTH,
  VALIDATOR_REQUIRE,
} from '../../shared/util/validators';
import Button from '../../shared/components/FormElements/Button';
import useForm from '../../shared/hooks/form-hook';
import Card from '../../shared/components/UIElements/Card';
import './Auth.css';
import { AuthContext } from '../../shared/contexts/auth-context';

const Auth = () => {
  const [isLoginMode, setIsLoginMode] = useState(true);
  const [formState, inputHandler, setFormData] = useForm(
    {
      email: {
        value: '',
        isValid: false,
      },
      password: {
        value: '',
        isValid: false,
      },
    },
    false
  );
  const authCtx = useContext(AuthContext);

  const loginHandler = (event) => {
    event.preventDefault();
    console.log(formState.inputs);
    authCtx.login();
  };

  const switchModeHandler = () => {
    if (isLoginMode) {
      setFormData(
        {
          ...formState.inputs,
          name: {
            value: '',
            isValid: false,
          },
        },
        false
      );
    } else {
      const newInputs = { ...formState.inputs };
      delete newInputs.name;
      setFormData(
        {
          ...newInputs,
        },
        formState.inputs.email.isValid && formState.inputs.password.isValid
      );
    }
    setIsLoginMode((pre) => !pre);
  };
  return (
    <Card className="authentication">
      <h2>Login Required</h2>
      <hr />
      <form onSubmit={loginHandler}>
        {!isLoginMode && (
          <Input
            id="name"
            element="input"
            type="text"
            label="Your Name"
            validators={[VALIDATOR_REQUIRE()]}
            errorText="Please enter a name."
            onInput={inputHandler}
          />
        )}
        <Input
          id="email"
          element="input"
          type="text"
          label="Email"
          validators={[VALIDATOR_REQUIRE(), VALIDATOR_EMAIL()]}
          errorText="Please enter a valid email."
          onInput={inputHandler}
        />
        <Input
          id="password"
          element="input"
          type="password"
          label="Password"
          validators={[VALIDATOR_REQUIRE(), VALIDATOR_MINLENGTH(6)]}
          errorText="Please enter a valid password. (must least 6 characters)."
          onInput={inputHandler}
        />
        <Button disabled={!formState.isValid}>
          {isLoginMode ? 'Login' : 'SIGNUP'}
        </Button>
      </form>
      <Button inverse onClick={switchModeHandler}>
        Switch to {isLoginMode ? 'SIGNUP' : 'LOGIN'}
      </Button>
    </Card>
  );
};

export default Auth;
