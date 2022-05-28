package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Movie;
import com.learnreactiveprogramming.exception.MovieException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceMockitoTest {

    @Mock
    private MovieInfoService movieInfoService;

    @Mock
    private ReviewService reviewService = new ReviewService();

    @InjectMocks
    private  MovieService movieService;

    @Test
    void getAllMovies() {

        Mockito.when(movieInfoService.movieInfoFlux()).thenCallRealMethod();
        Mockito.when(reviewService.retrieveReviewsFlux(Mockito.anyLong())).thenCallRealMethod();

        Flux<Movie> movieFlux = movieService.getAllMovies().log();

        StepVerifier.create(movieFlux)
                .expectNextCount(3)
                .verifyComplete();
    }
    @Test
    void getAllMovies_error_handle() {

        Mockito.when(movieInfoService.movieInfoFlux()).thenCallRealMethod();
        Mockito.when(reviewService.retrieveReviewsFlux(Mockito.anyLong())).thenThrow(IllegalArgumentException.class);

        Flux<Movie> movieFlux = movieService.getAllMovies().log();

        StepVerifier.create(movieFlux)
                .expectError(MovieException.class)
                .verify();
    }
}
