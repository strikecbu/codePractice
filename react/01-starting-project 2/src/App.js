import React, { useState, useEffect, useCallback } from 'react';

import MoviesList from './components/MoviesList';
import './App.css';

function App() {
  const [movies, setMovies] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchMoiveHandler = useCallback(async () => {
    setIsLoading(true);
    setError(null);

    try {
      const response = await fetch('https://swapi.dev/api/films');
      if (!response.ok) {
        throw new Error('Something goes wrong...')
      }

      const jsonData = await response.json();

      const movies = jsonData.results.map(
        movie => {
          return {
            id: movie.episode_id,
            title: movie.title,
            openingText: movie.opening_crawl,
            releaseDate: movie.release_date
          }
        });
      setMovies(movies);
    } catch (error) {
      setError(error.message)
    }

    setIsLoading(false);
  }, []);

  useEffect(() => {
    fetchMoiveHandler();
  }, [fetchMoiveHandler]);



  let contents = <p>Found no movies.</p>

  if (isLoading) {
    contents = <p>Loading...</p>
  }

  if (error) {
    contents = <p>{error}</p>
  }

  if (!isLoading && movies && movies.length > 0) {
    contents = <MoviesList movies={movies} />
  }

  return (
    <React.Fragment>
      <section>
        <button onClick={fetchMoiveHandler}>Fetch Movies</button>
      </section>
      <section>
        {contents}
      </section>
    </React.Fragment>
  );
}

export default App;
