import {useParams, Route} from 'react-router-dom'
import { Fragment } from 'react';
import Comments from '../components/comments/Comments';
import HighlightedQuote from '../components/quotes/HighlightedQuote';
import NoQuotesFound from '../components/quotes/NoQuotesFound';

const DUMMY_QUOTES = [
    { id: 'p1', author: 'andy', text: 'React is fun!'},
    { id: 'p2', author: 'andy chen', text: 'React is great!'}
];

const QuoteDetail = (props) => {
    const params = useParams();

    const quote = DUMMY_QUOTES.find( (quote) => quote.id === params.quoteId)

    if(!quote) {
        return <NoQuotesFound />
    }

    return (
        <Fragment>
            <HighlightedQuote text={quote.text} author={quote.author} />
            <Route path={`/quotes/${params.quoteId}/comments`}>
                <Comments />
            </Route>
        </Fragment>
    );
}

export default QuoteDetail;