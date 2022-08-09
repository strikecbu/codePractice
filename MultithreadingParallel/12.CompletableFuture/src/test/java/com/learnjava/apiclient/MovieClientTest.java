package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class MovieClientTest {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();

    private final MovieClient movieClient = new MovieClient(webClient);

    @RepeatedTest(10)
    void retrieveMovie() {
        startTimer();
        Long movieInfoId = 1L;

        Movie movie = movieClient.retrieveMovie(movieInfoId);

        timeTaken();
        assert movie != null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assertEquals(1, movie.getReviewList().size());
    }

    @RepeatedTest(10)
    void retrieveMovie_CF() {
        startTimer();
        Long movieInfoId = 1L;

        Movie movie = movieClient.retrieveMovie_CF(movieInfoId).join();

        timeTaken();
        assert movie != null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assertEquals(1, movie.getReviewList().size());
    }

    @RepeatedTest(10)
    void retrieveMovies() {
        startTimer();
        List<Long> movieInfoIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L );

        var movies = movieClient.retrieveMovies(movieInfoIds);

        timeTaken();
        assertEquals(7, movies.size());
    }
    @RepeatedTest(10)
    void retrieveMovies_CF() {
        startTimer();
        List<Long> movieInfoIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L );

        var movies = movieClient.retrieveMovies_CF(movieInfoIds);

        timeTaken();
        assertEquals(7, movies.size());
    }
    @RepeatedTest(10)
    void retrieveMovies_allOf() {
        startTimer();
        List<Long> movieInfoIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L );

        var movies = movieClient.retrieveMovies_allOf(movieInfoIds);

        timeTaken();
        assertEquals(7, movies.size());
    }
}
