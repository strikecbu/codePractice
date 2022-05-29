package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MovieServiceRestClientTest {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();

    private final MovieInfoService movieInfoService = new MovieInfoService(webClient);

    private final ReviewService reviewService = new ReviewService(webClient);

    MovieService movieService = new MovieService(movieInfoService, reviewService);

    @Test
    void getAllMovies_RestClient() {

        Flux<Movie> movieFlux = movieService.getAllMovies_RestClient()
                .log();

        StepVerifier.create(movieFlux)
                .expectNextCount(7)
                .verifyComplete();
    }
    @Test
    void getMovieById_RestClient() {

        Mono<Movie> movieFlux = movieService.getMovieById_RestClient(1L)
                .log();

        StepVerifier.create(movieFlux)
                .expectNextCount(1)
                .verifyComplete();
    }
}
