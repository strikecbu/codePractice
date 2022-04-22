import React, { useCallback, useReducer } from 'react';
import { VALIDATOR_REQUIRE } from '../../shared/util/validators';
import Input from '../../shared/components/FormElements/Input';

import './NewPlace.css';
import Button from '../../shared/components/FormElements/Button';

const newPlaceReducer = (state, action) => {
  switch (action.type) {
    case 'CHANGE_INPUT':
      let isFormValid = true;
      for (let key of Object.keys(state.inputs)) {
        if (key === action.id) {
          isFormValid = isFormValid && action.isValid;
        } else {
          isFormValid = isFormValid && state.inputs[key]['isValid'];
        }
      }
      return {
        ...state,
        inputs: {
          ...state.inputs,
          [action.id]: { value: action.value, isValid: action.isValid },
        },
        isValid: isFormValid,
      };
    default:
      return state;
  }
};

const NewPlace = () => {
  const [allFormState, dispatch] = useReducer(newPlaceReducer, {
    inputs: {
      title: {
        value: '',
        isValid: false,
      },
      description: {
        value: '',
        isValid: false,
      },
    },
    isValid: false,
  });

  const onInputHandler = useCallback((id, value, isValid) => {
    dispatch({ type: 'CHANGE_INPUT', id, value, isValid });
  }, []);

  const addNewPlaceHandler = (event) => {
    event.preventDefault();
  };

  return (
    <form className="place-form" onSubmit={addNewPlaceHandler}>
      <Input
        id="title"
        element="input"
        type="text"
        label="Title"
        validators={[VALIDATOR_REQUIRE()]}
        errorText="Please enter a valid title."
        onInput={onInputHandler}
      />
      <Input
        id="description"
        element="textarea"
        label="Description"
        validators={[VALIDATOR_REQUIRE()]}
        errorText="Please enter a valid description."
        onInput={onInputHandler}
      />
      <Button disabled={!allFormState.isValid}>Submit</Button>
    </form>
  );
};

export default NewPlace;
