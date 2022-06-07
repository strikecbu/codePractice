package com.reactivespring.routes;

import com.reactivespring.domain.Review;
import com.reactivespring.handler.ReviewHandler;
import com.reactivespring.repository.ReviewReactorRepository;
import com.reactivespring.route.RouterConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

@WebFluxTest
@AutoConfigureWebTestClient
@ContextConfiguration(classes = {ReviewHandler.class, RouterConfig.class})
public class ReviewsUnitTest {
    private final String API_URL = "/v1/reviews";
    @MockBean
    ReviewReactorRepository repository;
    @Autowired
    private WebTestClient webTestClient;

    private Flux<Review> getDummyFlux() {
        var reviewsList = List.of(new Review("1", 1L, "Awesome Movie", 9.0),
                new Review(null, 1L, "Awesome Movie1", 9.0),
                new Review(null, 2L, "Excellent Movie", 8.0));
        return Flux.fromIterable(reviewsList);
    }

    @Test
    void getAllReviews() {
        given(repository.findAll()).willReturn(getDummyFlux());

        webTestClient.get()
                .uri(API_URL)
                .exchange()
                .expectBodyList(Review.class)
                .hasSize(3);
    }

    @Test
    void getAllReviewsWithMovieInfoId() {
        long movieInfoId = 1L;
        given(repository.findByMovieInfoId(isA(Long.class)))
                .willReturn(
                        getDummyFlux().filter(review -> review.getMovieInfoId() == movieInfoId)
                );
        URI uri = UriComponentsBuilder.fromUriString(API_URL)
                .queryParam("movieInfoId", movieInfoId)
                .build()
                .toUri();

        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectBodyList(Review.class)
                .hasSize(2);
    }

    @Test
    void addReview() {
        Review review = new Review(null, 1L, "WOW Movie omg", 9.0);
        given(repository.save(isA(Review.class))).willReturn(Mono.just(review));

        webTestClient.post()
                .uri(API_URL)
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Review.class)
                .consumeWith(reviewEntityExchangeResult -> {
                    Review responseBody = reviewEntityExchangeResult.getResponseBody();
                    assert responseBody != null;
                    assertEquals(review.getComment(), responseBody.getComment());
                });
    }

    @Test
    void updateReview() {
        Review review = new Review("1", 1L, "WOW Movie omg", 9.0);
        given(repository.findById(isA(String.class))).willReturn(Mono.just(review));
        given(repository.save(isA(Review.class))).willReturn(Mono.just(review));

        webTestClient.put()
                .uri(API_URL + "/{id}", "1")
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Review.class)
                .consumeWith(reviewEntityExchangeResult -> {
                    Review responseBody = reviewEntityExchangeResult.getResponseBody();
                    assert responseBody != null;
                    assertEquals(review.getComment(), responseBody.getComment());
                });
    }

    @Test
    void updateReview_404() {
        given(repository.findById(isA(String.class))).willReturn(Mono.empty());

        Review review = new Review("1", 1L, "WOW Movie omg", 9.0);
        webTestClient.put()
                .uri(API_URL + "/{id}", "123")
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void deleteReview() {
        Review review = new Review("1", 1L, "WOW Movie omg", 9.0);
        given(repository.findById(isA(String.class))).willReturn(Mono.just(review));
        given(repository.delete(isA(Review.class))).willReturn(Mono.empty());

        webTestClient.delete()
                .uri(API_URL + "/{id}", "1")
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    void deleteReview_404() {
        given(repository.findById(isA(String.class))).willReturn(Mono.empty());

        webTestClient.delete()
                .uri(API_URL + "/{id}", "1123")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
