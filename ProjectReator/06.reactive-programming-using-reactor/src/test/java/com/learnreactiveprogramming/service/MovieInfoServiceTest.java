package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.MovieInfo;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


class MovieInfoServiceTest {

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/movies").build();

    private final MovieInfoService movieInfoService = new MovieInfoService(webClient);

    @Test
    void movieInfoFlux_with_webclient() {
        Flux<MovieInfo> movieInfoFlux = movieInfoService.movieInfoFlux_with_webclient().log();

        StepVerifier.create(movieInfoFlux)
                .expectNextCount(7)
                .verifyComplete();
    }
    @Test
    void movieInfoById_with_webclient() {
        Mono<MovieInfo> movieInfoFlux = movieInfoService.movieInfoById_with_webclient(1).log();

        StepVerifier.create(movieInfoFlux)
                .expectNextCount(1)
                .verifyComplete();
    }
}
