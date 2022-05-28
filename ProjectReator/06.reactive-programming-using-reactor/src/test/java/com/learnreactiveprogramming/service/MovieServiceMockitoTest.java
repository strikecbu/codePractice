package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Movie;
import com.learnreactiveprogramming.exception.MovieException;
import com.learnreactiveprogramming.exception.NetworkException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class MovieServiceMockitoTest {

    @Mock
    private MovieInfoService movieInfoService;

    @Mock
    private ReviewService reviewService = new ReviewService();

    @InjectMocks
    private MovieService movieService;

    @Test
    void getAllMovies() {

        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();
        Mockito.when(reviewService.retrieveReviewsFlux(Mockito.anyLong()))
                .thenCallRealMethod();

        Flux<Movie> movieFlux = movieService.getAllMovies()
                .log();

        StepVerifier.create(movieFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void getAllMovies_error_handle() {

        var errorMsg = "Error occurred in review service.";
        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();
        Mockito.when(reviewService.retrieveReviewsFlux(Mockito.anyLong()))
                .thenThrow(new NetworkException(errorMsg));

        Flux<Movie> movieFlux = movieService.getAllMovies()
                .log();

        StepVerifier.create(movieFlux)
                .expectError(MovieException.class)
                .verify();
    }

    @Test
    void getAllMovies_retry() {

        var errorMsg = "Error occurred in review service.";
        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();
        Mockito.when(reviewService.retrieveReviewsFlux(Mockito.anyLong()))
                .thenThrow(new NetworkException(errorMsg));

        Flux<Movie> movieFlux = movieService.getAllMovies_retry()
                .log();

        StepVerifier.create(movieFlux)
                .expectError(MovieException.class)
                .verify();

        Mockito.verify(reviewService, Mockito.times(4))
                .retrieveReviewsFlux(Mockito.anyLong());
    }
    @Test
    void getAllMovies_retryWhen() {

        var errorMsg = "Error occurred in review service.";
        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();
        Mockito.when(reviewService.retrieveReviewsFlux(Mockito.anyLong()))
                .thenThrow(new NetworkException(errorMsg));

        Flux<Movie> movieFlux = movieService.getAllMovies_retryWhen()
                .log();

        StepVerifier.create(movieFlux)
                .expectError(MovieException.class)
                .verify();

        Mockito.verify(reviewService, Mockito.times(4))
                .retrieveReviewsFlux(Mockito.anyLong());
    }
    @Test
    void getAllMovies_repeat() {
        Mockito.when(movieInfoService.movieInfoFlux())
                .thenCallRealMethod();
        Mockito.when(reviewService.retrieveReviewsFlux(Mockito.anyLong()))
                .thenCallRealMethod();

        var noRepeatTimes = 3L;
        Flux<Movie> movieFlux = movieService.getAllMovies_repeat_n(noRepeatTimes)
                .log();

        StepVerifier.create(movieFlux)
                .expectNextCount(9)
                .thenCancel()
                .verify();

        Mockito.verify(reviewService, Mockito.times(9))
                .retrieveReviewsFlux(Mockito.anyLong());
    }
}
