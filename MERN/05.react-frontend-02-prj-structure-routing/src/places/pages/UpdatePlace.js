import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import { VALIDATOR_REQUIRE } from '../../shared/util/validators';
import Input from '../../shared/components/FormElements/Input';
import Button from '../../shared/components/FormElements/Button';
import useForm from '../../shared/hooks/form-hook';
import './PlaceForm.css';
import Card from '../../shared/components/UIElements/Card';

const DUMMY_PLACES = [
  {
    id: 'p1',
    title: 'Empire State Building',
    description: 'One of the most famous sky scrapers in the world!',
    imageUrl:
      'https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/NYC_Empire_State_Building.jpg/640px-NYC_Empire_State_Building.jpg',
    address: '20 W 34th St, New York, NY 10001',
    location: {
      lat: 40.7484405,
      lng: -73.9878584,
    },
    creator: 'u1',
  },
  {
    id: 'p2',
    title: 'Empire State Building',
    description: 'One of the most famous sky scrapers in the world!',
    imageUrl:
      'https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/NYC_Empire_State_Building.jpg/640px-NYC_Empire_State_Building.jpg',
    address: '20 W 34th St, New York, NY 10001',
    location: {
      lat: 40.7484405,
      lng: -73.9878584,
    },
    creator: 'u2',
  },
];

const UpdatePlace = () => {
  const placeId = useParams()['placeId'];
  const [isLoading, setLoading] = useState(true);

  const [formState, inputHandler, setForm] = useForm(
    {
      title: {
        value: '',
        isValid: false,
      },
      description: {
        value: '',
        isValid: false,
      },
    },
    false
  );

  const selectedPlace = DUMMY_PLACES.find((place) => place.id === placeId);

  useEffect(() => {
    setForm(
      {
        title: {
          value: (selectedPlace && selectedPlace.title) || '',
          isValid: true,
        },
        description: {
          value: (selectedPlace && selectedPlace.description) || '',
          isValid: true,
        },
      },
      false
    );
    setLoading(false);
  }, [setForm, selectedPlace]);

  const updatePlaceHandler = (event) => {
    event.preventDefault();
    console.log(formState.inputs);
  };

  if (!selectedPlace) {
    return (
      <div className="center">
        <Card>
          <h2>Not found any place</h2>
        </Card>
      </div>
    );
  }
  if (isLoading) {
    return (
      <div className="center">
        <h2>Loading...</h2>
      </div>
    );
  }

  return (
    <form className="place-form" onSubmit={updatePlaceHandler}>
      <Input
        id="title"
        element="input"
        type="text"
        label="Title"
        validators={[VALIDATOR_REQUIRE()]}
        errorText="Please enter a valid title."
        initalValue={formState.inputs.title.value}
        initalValid={formState.inputs.title.isValid}
        onInput={inputHandler}
      />
      <Input
        id="description"
        element="textarea"
        label="Description"
        validators={[VALIDATOR_REQUIRE()]}
        errorText="Please enter a valid description."
        initalValue={formState.inputs.description.value}
        initalValid={formState.inputs.description.isValid}
        onInput={inputHandler}
      />
      <Button disabled={!formState.isValid}>Update</Button>
    </form>
  );
};

export default UpdatePlace;
