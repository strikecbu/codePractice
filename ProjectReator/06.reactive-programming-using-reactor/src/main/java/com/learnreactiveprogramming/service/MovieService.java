package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Movie;
import com.learnreactiveprogramming.domain.Revenue;
import com.learnreactiveprogramming.domain.Review;
import com.learnreactiveprogramming.exception.MovieException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.List;

@Slf4j
public class MovieService {
    private final MovieInfoService movieInfoService;
    private final ReviewService reviewService;

    private final RevenueService revenueService;


    public MovieService(MovieInfoService movieInfoService, ReviewService reviewService, RevenueService revenueService) {
        this.movieInfoService = movieInfoService;
        this.reviewService = reviewService;
        this.revenueService = revenueService;
    }

    public Flux<Movie> getAllMovies() {
        return getAllMoviesFlux();
    }

    public Flux<Movie> getAllMovies_retry() {
        return getAllMoviesFlux().retry(3);
    }

    public Flux<Movie> getAllMovies_retryWhen() {
        RetryBackoffSpec backoff =
                Retry.fixedDelay(3, Duration.ofMillis(500))
                        .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure())));
        return getAllMoviesFlux().retryWhen(backoff);
    }

    public Flux<Movie> getAllMovies_repeat() {
        RetryBackoffSpec backoff =
                Retry.fixedDelay(3, Duration.ofMillis(500))
                        .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure())));
        return getAllMoviesFlux()
                .retryWhen(backoff)
                .repeat();
    }

    public Flux<Movie> getAllMovies_repeat_n(long n) {
        RetryBackoffSpec backoff =
                Retry.fixedDelay(3, Duration.ofMillis(500))
                        .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure())));
        return getAllMoviesFlux()
                .retryWhen(backoff)
                .repeat(n);
    }

    private Flux<Movie> getAllMoviesFlux() {
        return movieInfoService.movieInfoFlux()
                .flatMap(movieInfo -> {
                    var listMono = reviewService.retrieveReviewsFlux(movieInfo.getMovieId())
                            .collectList();
                    return listMono.map(reviews -> new Movie(movieInfo.getMovieId(), movieInfo, reviews));
                })
                .onErrorMap(e -> {
                    log.error("Some error occurred", e);
                    return new MovieException(e);
                });
    }

    public Flux<Movie> getAllMoviesWithRevenueFlux() {
        return movieInfoService.movieInfoFlux()
                .flatMap(movieInfo -> {

                    var listMono = reviewService.retrieveReviewsFlux(movieInfo.getMovieId())
                            .collectList();
                    return listMono.map(reviews -> new Movie(movieInfo.getMovieId(), movieInfo, reviews));
                })
                .flatMap(aMovie -> Mono.fromCallable(() -> revenueService.getRevenue(aMovie.getMovieId()))
                        .subscribeOn(Schedulers.boundedElastic())
                        .map(revenue -> {
                            aMovie.setRevenue(revenue);
                            return aMovie;
                        }))
                .onErrorMap(e -> {
                    log.error("Some error occurred", e);
                    return new MovieException(e);
                });
    }

    public Mono<Movie> getMovieById(long movieId) {
        var movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
        Mono<List<Review>> listMono = reviewService.retrieveReviewsFlux(movieId)
                .collectList();
        return Mono.zip(movieInfoMono, listMono)
                .map(tuple -> new Movie(tuple.getT1()
                        .getMovieId(), tuple.getT1(), tuple.getT2()));
    }

    public Mono<Movie> getMovieByIdWithRevenue(long movieId) {
        var movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
        Mono<List<Review>> listMono = reviewService.retrieveReviewsFlux(movieId)
                .collectList();
        Mono<Revenue> revenueMono = Mono.fromCallable(() -> revenueService.getRevenue(movieId))
                .subscribeOn(Schedulers.boundedElastic());
        return Mono.zip(movieInfoMono, listMono)
                .map(tuple -> new Movie(tuple.getT1()
                        .getMovieId(), tuple.getT1(), tuple.getT2()))
                .zipWith(revenueMono, (movie, revenue) -> {
                    movie.setRevenue(revenue);
                    return movie;
                });
    }
}
