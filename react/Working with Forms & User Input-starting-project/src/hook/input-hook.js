import { useReducer } from 'react';


const inputReducer = (state, action) => {
    const type = action.type;

    if(inputActions.input === type) {
        return {...state, value: action.payload}
    }
    if(inputActions.check === type) {
        return {...state, isValueValidate: action.payload}
    }
    if(inputActions.blur === type) {
        return {...state, change: true}
    }
    if(inputActions.reset === type) {
        return {value: '', change: false, isValueValidate: false}
    }
}

const useInput = (validateHandler) => {
    const [state, dispatch] = useReducer(inputReducer, {
        value: '',
        change: false,
        isValueValidate: false
    });
    
    let isValueHasError = !state.isValueValidate && state.change;

    const onInputChange =  (event) => {
        dispatch({type: inputActions.input, payload: event.target.value});
        dispatch({type: inputActions.check, payload: validateHandler(event.target.value)});
    }
    const onBlur =  () => {
        dispatch({type: inputActions.blur});
        dispatch({type: inputActions.check, payload: validateHandler(state.value)});
    }

    const reset = () => {
        dispatch({type: inputActions.reset})
    }

    return {
        value: state.value,
        isValueValidate: state.isValueValidate,
        isValueHasError,
        onInputChange,
        onBlur,
        reset,
    }
}

export const inputActions = {
    input: "INPUT",
    check: "CHECK",
    blur: "BLUR",
    reset: "RESET"
}

export default useInput;