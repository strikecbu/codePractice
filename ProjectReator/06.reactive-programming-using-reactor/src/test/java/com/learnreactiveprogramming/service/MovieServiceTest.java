package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Movie;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieServiceTest {

    private final MovieInfoService movieInfoService = new MovieInfoService();
    private final ReviewService reviewService = new ReviewService();
    private final MovieService movieService = new MovieService(movieInfoService, reviewService);

    @Test
    void getAllMovies() {
        Flux<Movie> movieFlux = movieService.getAllMovies()
                .log();

        StepVerifier.create(movieFlux)
                .assertNext(movie -> {
                    assertEquals("Batman Begins",
                            movie.getMovie()
                                    .getName());
                    assertEquals(2,
                            movie.getReviewList()
                                    .size());
                })
                .assertNext(movie -> {
                    assertEquals("The Dark Knight",
                            movie.getMovie()
                                    .getName());
                    assertEquals(2,
                            movie.getReviewList()
                                    .size());
                })
                .assertNext(movie -> {
                    assertEquals("Dark Knight Rises",
                            movie.getMovie()
                                    .getName());
                    assertEquals(2,
                            movie.getReviewList()
                                    .size());
                })
                .verifyComplete();
    }

    @Test
    void getMovieById() {
        Mono<Movie> movieMono = movieService.getMovieById(1L)
                .log();
        StepVerifier.create(movieMono)
                .assertNext(movie -> {
                    assertEquals("Batman Begins",
                            movie.getMovie()
                                    .getName());
                    assertEquals(2,
                            movie.getReviewList()
                                    .size());
                })
                .verifyComplete();
    }
}
