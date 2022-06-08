package com.reactivespring.controller;

import com.reactivespring.domain.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8055)
@AutoConfigureWebTestClient
@TestPropertySource(properties = {
        "apiUrl.movieInfo=http://localhost:8055/v1/movieInfos",
        "apiUrl.movieView=http://localhost:8055/v1/reviews"
})
public class MoviesControllerIntgTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void getMovieById() {
        String movieId = "abc";

        stubFor(get(urlEqualTo("/v1/movieInfos/" + movieId))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("movieinfo.json")
                ));

        stubFor(get(urlPathEqualTo("/v1/reviews"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("reviews.json")
                ));

        webClient.get()
                .uri("/v1/movies/" + movieId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Movie.class)
                .consumeWith(movieEntityExchangeResult -> {
                    Movie movie = movieEntityExchangeResult.getResponseBody();
                    assert Objects.requireNonNull(movie)
                            .getReviewList()
                            .size() == 2;
                    assertEquals("Batman Begins",
                            movie.getMovieInfo()
                                    .getName());
                });
    }

    @Test
    public void getMovieById_movieInfo404() {
        String movieId = "abc";

        stubFor(get(urlEqualTo("/v1/movieInfos/" + movieId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                ));

        stubFor(get(urlPathEqualTo("/v1/reviews"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("reviews.json")
                ));

        webClient.get()
                .uri("/v1/movies/" + movieId)
                .exchange()
                .expectStatus()
                .isNotFound();

        verify(1, getRequestedFor(urlEqualTo("/v1/movieInfos/" + movieId)));
    }

    @Test
    public void getMovieById_review404() {
        String movieId = "abc";

        stubFor(get(urlEqualTo("/v1/movieInfos/" + movieId))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("movieinfo.json")
                ));

        stubFor(get(urlPathEqualTo("/v1/reviews"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                ));

        webClient.get()
                .uri("/v1/movies/" + movieId)
                .exchange()
                .expectStatus()
                .isBadRequest();

        verify(1, getRequestedFor(urlPathEqualTo("/v1/reviews")));

    }

    @Test
    public void getMovieById_movieInfo500() {
        String movieId = "abc";

        stubFor(get(urlEqualTo("/v1/movieInfos/" + movieId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withBody("MovieInfo service is not available.")
                ));

        stubFor(get(urlPathEqualTo("/v1/reviews"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("reviews.json")
                ));

        webClient.get()
                .uri("/v1/movies/" + movieId)
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody(String.class)
                .isEqualTo("MovieInfo service is not available.");

        verify(4, getRequestedFor(urlEqualTo("/v1/movieInfos/" + movieId)));
    }

    @Test
    public void getMovieById_review500() {
        String movieId = "abc";

        stubFor(get(urlEqualTo("/v1/movieInfos/" + movieId))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("movieinfo.json")
                ));

        stubFor(get(urlPathEqualTo("/v1/reviews"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withBody("Review service is not available.")
                ));

        webClient.get()
                .uri("/v1/movies/" + movieId)
                .exchange()
                .expectStatus()
                .is5xxServerError()
                .expectBody(String.class)
                .isEqualTo("Review service is not available.");

        verify(4, getRequestedFor(urlPathEqualTo("/v1/reviews")));
    }

}
