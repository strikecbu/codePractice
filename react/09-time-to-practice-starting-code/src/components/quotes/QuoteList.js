import { Fragment } from 'react';
import { useHistory, useLocation } from 'react-router-dom';

import QuoteItem from './QuoteItem';
import classes from './QuoteList.module.css';

const sortQuotesFn = (quotes, ascending) => {
  return quotes.sort((a , b) => {
    if(ascending) {
      return a.id > b.id ? 1 : -1;
    } else {
      return a.id > b.id ? -1 : 1;
    }
  });
}

const QuoteList = (props) => {

  const history = useHistory();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  const isSortAscening = queryParams.get('sort') === 'asc';
  const sortQuotes = sortQuotesFn(props.quotes, isSortAscening);

  const sortingHandler = () => {
    history.push('/quotes?sort=' + (isSortAscening ? 'desc' : 'asc'));
  }

  return (
    <Fragment>
      <div className={classes.sorting}>
        <button onClick={sortingHandler}>Sort {isSortAscening ? 'Descending' : 'Ascending'}</button>
      </div>
      <ul className={classes.list}>
        {sortQuotes.map((quote) => (
          <QuoteItem
            key={quote.id}
            id={quote.id}
            author={quote.author}
            text={quote.text}
          />
        ))}
      </ul>
    </Fragment>
  );
};

export default QuoteList;
