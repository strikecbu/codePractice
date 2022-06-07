 package com.reactivespring.handler;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewReactorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ReviewHandlerTest {

    private final String API_URL = "/v1/reviews";
    @Autowired
    ReviewReactorRepository reviewReactiveRepository;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {

        var reviewsList = List.of(new Review("1", 1L, "Awesome Movie", 9.0),
                new Review(null, 1L, "Awesome Movie1", 9.0),
                new Review(null, 2L, "Excellent Movie", 8.0));
        reviewReactiveRepository.saveAll(reviewsList)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        reviewReactiveRepository.deleteAll()
                .block();
    }

    @Test
    void getAllReviews() {
        webTestClient.get()
                .uri(API_URL)
                .exchange()
                .expectBodyList(Review.class)
                .hasSize(3);
    }

    @Test
    void getAllReviewsWithMovieInfoId() {
        URI uri = UriComponentsBuilder.fromUriString(API_URL)
                .queryParam("movieInfoId", 1L)
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
        webTestClient.post().uri(API_URL)
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
        Review review = new Review("1", 1L, "WOW Movie omg", 9.0);
        webTestClient.put().uri(API_URL + "/{id}", "123")
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void deleteReview() {
        webTestClient.delete().uri(API_URL + "/{id}", "1")
                .exchange()
                .expectStatus()
                .isNoContent();
    }
    @Test
    void deleteReview_404() {
        webTestClient.delete().uri(API_URL + "/{id}", "1123")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
