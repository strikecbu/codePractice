package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ReviewServiceTest {


    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();

    private ReviewService reviewService = new ReviewService(webClient);

    @Test
    void retrieveReviewsFlux_RestClient() {

        Flux<Review> reviewFlux = reviewService.retrieveReviewsFlux_RestClient(1L)
                .log();

        StepVerifier.create(reviewFlux)
                .expectNextCount(1)
                .verifyComplete();
    }
}
