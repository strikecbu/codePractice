import QuoteList from "../components/quotes/QuoteList";

const DUMMY_QUOTES = [
    { id: 'p1', author: 'andy', text: 'React is fun!'},
    { id: 'p2', author: 'andy chen', text: 'React is great!'}
];

const AllQuotes = () => {
    return (
        <QuoteList quotes={DUMMY_QUOTES} />
    );
}

export default AllQuotes;