package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Movie;
import com.learnreactiveprogramming.domain.Review;
import com.learnreactiveprogramming.exception.MovieException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
@Slf4j
public class MovieService {
    private final MovieInfoService movieInfoService;
    private final ReviewService reviewService;

    public MovieService(MovieInfoService movieInfoService, ReviewService reviewService) {
        this.movieInfoService = movieInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Movie> getAllMovies() {
        return movieInfoService.movieInfoFlux()
                .flatMap(movieInfo -> {
                    var listMono = reviewService.retrieveReviewsFlux(movieInfo.getMovieId())
                            .collectList();
                    return listMono.map(reviews -> new Movie(movieInfo.getMovieId(), movieInfo, reviews));
                }).onErrorMap(e -> {
                    log.error("Some error occurred", e);
                    return new MovieException(e);
                });
    }

    public Mono<Movie> getMovieById(long movieId) {
        var movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
        Mono<List<Review>> listMono = reviewService.retrieveReviewsFlux(movieId)
                .collectList();
        return Mono.zip(movieInfoMono, listMono).map(tuple -> new Movie(tuple.getT1()
                .getMovieId(), tuple.getT1(), tuple.getT2()));
    }
}
