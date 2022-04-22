import React from 'react';

import {
  VALIDATOR_MINLENGTH,
  VALIDATOR_REQUIRE,
} from '../../shared/util/validators';
import Input from '../../shared/components/FormElements/Input';
import Button from '../../shared/components/FormElements/Button';
import useForm from '../../shared/hooks/form-hook';
import './PlaceForm.css';

const NewPlace = () => {
  const [allFormState, onInputHandler] = useForm(
    {
      title: {
        value: '',
        isValid: false,
      },
      description: {
        value: '',
        isValid: false,
      },
      address: {
        value: '',
        isValid: false,
      },
    },
    false
  );

  const addNewPlaceHandler = (event) => {
    event.preventDefault();
    console.log(allFormState.inputs);
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
      <Input
        id="address"
        element="input"
        type="text"
        label="Address"
        validators={[VALIDATOR_REQUIRE(), VALIDATOR_MINLENGTH(5)]}
        errorText="Please enter a valid address. (min 5. characters)"
        onInput={onInputHandler}
      />
      <Button disabled={!allFormState.isValid}>Submit</Button>
    </form>
  );
};

export default NewPlace;
