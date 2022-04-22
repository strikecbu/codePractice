import { useCallback, useReducer } from 'react';

const formReducer = (state, action) => {
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
const useForm = (initialInputs, initialValidity) => {
  const [formState, dispatch] = useReducer(formReducer, {
    inputs: initialInputs,
    isValid: initialValidity,
  });

  const inputHandler = useCallback((id, value, isValid) => {
    dispatch({ type: 'CHANGE_INPUT', id, value, isValid });
  }, []);

  return [formState, inputHandler];
};

export default useForm;
