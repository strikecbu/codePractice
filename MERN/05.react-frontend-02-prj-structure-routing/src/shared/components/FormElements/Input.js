import React, { useReducer } from 'react';

import './Input.css';
import { validate } from '../../util/validators';

const inputReducer = (state, action) => {
  switch (action.type) {
    case 'CHANGE':
      return {
        ...state,
        value: action.val,
        isValid: validate(action.val, action.validators),
      };
    case 'TOUCH':
      return {
        ...state,
        isTouch: true,
      };
    default:
      return state;
  }
};

const Input = (props) => {
  const [enteredState, dispatch] = useReducer(inputReducer, {
    value: '',
    isValid: false,
  });

  const onChangeHandler = (event) => {
    dispatch({
      type: 'CHANGE',
      val: event.target.value,
      validators: props.validators,
    });
  };

  const onTouchHandler = () => {
    dispatch({ type: 'TOUCH' });
  };

  const content =
    props.element === 'input' ? (
      <input
        id={props.id}
        type={props.type || 'text'}
        value={enteredState.value}
        onChange={onChangeHandler}
        onBlur={onTouchHandler}
      />
    ) : (
      <textarea
        id={props.id}
        rows={props.rows || 3}
        value={enteredState.value}
        onChange={onChangeHandler}
        onBlur={onTouchHandler}
      />
    );

  return (
    <div
      className={`form-control ${
        enteredState.isTouch && !enteredState.isValid && 'form-control--invalid'
      }`}
    >
      <label htmlFor={props.id}>{props.label}</label>
      {content}
      {enteredState.isTouch && !enteredState.isValid && (
        <p>{props.errorText}</p>
      )}
    </div>
  );
};

export default Input;
