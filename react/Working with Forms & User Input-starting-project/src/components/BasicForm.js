import useInput from '../hook/input-hook'

const BasicForm = (props) => {

  const {
    value: firstNameValue,
    isValueValidate: isFirstNameValid,
    isValueHasError: isFirstNameHasError,
    onInputChange: firstNameChangeHandler,
    onBlur: firstNameBlurHandler,
    reset: firstNameResetHandler,
  } = useInput(value => value.trim().length > 0);

  const {
    value: lastNameValue,
    isValueValidate: isLastNameValid,
    isValueHasError: isLastNameHasError,
    onInputChange: lastNameChangeHandler,
    onBlur: lastNameBlurHandler,
    reset: lastNameResetHandler,
  } = useInput(value => value.trim().length > 0);

  const {
    value: emailValue,
    isValueValidate: isEmailValid,
    isValueHasError: isEmailHasError,
    onInputChange: emailChangeHandler,
    onBlur: emailBlurHandler,
    reset: emailResetHandler,
  } = useInput(value => value.includes('@'));

  let isFormValid = isFirstNameValid && isLastNameValid && isEmailValid;
  const submitHandler = (event) => {
    event.preventDefault();
    if (!isFormValid) {
      return;
    }
    firstNameResetHandler();
    lastNameResetHandler();
    emailResetHandler();
    console.log('submit is completed.')
  }

  const firstNameClasses = isFirstNameHasError ? 'form-control invalid' : 'form-control'
  const lastNameClasses = isLastNameHasError ? 'form-control invalid' : 'form-control'
  const emailClasses = isEmailHasError ? 'form-control invalid' : 'form-control'

  return (
    <form onSubmit={submitHandler}>
      <div className='control-group'>
        <div className={firstNameClasses}>
          <label htmlFor='name'>First Name</label>
          <input type='text' id='name'
            value={firstNameValue}
            onChange={firstNameChangeHandler}
            onBlur={firstNameBlurHandler} />
          {isFirstNameHasError && <p className=''>First Name should NOT empty.</p>}
        </div>

        <div className={lastNameClasses}>
          <label htmlFor='name'>Last Name</label>
          <input type='text' id='name'
            value={lastNameValue}
            onChange={lastNameChangeHandler}
            onBlur={lastNameBlurHandler} />
          {isLastNameHasError && <p className=''>Last Name should NOT empty.</p>}
        </div>
      </div>
      <div className={emailClasses}>
        <label htmlFor='name'>E-Mail Address</label>
        <input type='text' id='name'
          value={emailValue}
          onChange={emailChangeHandler}
          onBlur={emailBlurHandler} />
        {isEmailHasError && <p className=''>Email should enter valid value.</p>}
      </div>
      <div className='form-actions'>
        <button disabled={!isFormValid}>Submit</button>
      </div>
    </form>
  );
};

export default BasicForm;
