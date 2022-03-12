import { useRef, useState, Fragment } from 'react';

import Card from '../UI/Card';
import LoadingSpinner from '../UI/LoadingSpinner';
import classes from './QuoteForm.module.css';
import { Prompt } from 'react-router-dom';

const QuoteForm = (props) => {
  const authorInputRef = useRef();
  const textInputRef = useRef();
  const [isEntering, setIsEntering] = useState(false);

  function submitFormHandler(event) {
    event.preventDefault();

    const enteredAuthor = authorInputRef.current.value;
    const enteredText = textInputRef.current.value;

    // optional: Could validate here

    props.onAddQuote({ author: enteredAuthor, text: enteredText });
  }

  const clickFormSubmitHandler = () => {
    setIsEntering(false);
  }

  const focusFormHanlder = () => {
    setIsEntering(true);
  }

  return (
    <Fragment>
      <Card>
        <Prompt when={isEntering} message='Are you sure to leave?'></Prompt>
        <form onFocus={focusFormHanlder} className={classes.form} onSubmit={submitFormHandler}>
          {props.isLoading && (
            <div className={classes.loading}>
              <LoadingSpinner />
            </div>
          )}

          <div className={classes.control}>
            <label htmlFor='author'>Author</label>
            <input type='text' id='author' ref={authorInputRef} />
          </div>
          <div className={classes.control}>
            <label htmlFor='text'>Text</label>
            <textarea id='text' rows='5' ref={textInputRef}></textarea>
          </div>
          <div className={classes.actions}>
            <button onClick={clickFormSubmitHandler} className='btn'>Add Quote</button>
          </div>
        </form>
      </Card>
    </Fragment>

  );
};

export default QuoteForm;