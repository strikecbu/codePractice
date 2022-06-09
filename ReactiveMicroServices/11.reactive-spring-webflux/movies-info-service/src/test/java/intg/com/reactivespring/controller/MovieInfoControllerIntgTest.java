package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class MovieInfoControllerIntgTest {

    private final String MOVIE_INFO_URL = "/v1/movieInfos";
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movieInfos = List.of(new MovieInfo(null,
                        "Batman Begins",
                        2005,
                        List.of("Christian Bale", "Michael Cane"),
                        LocalDate.parse("2005-06-15")),
                new MovieInfo(null,
                        "The Dark Knight",
                        2008,
                        List.of("Christian Bale", "HeathLedger"),
                        LocalDate.parse("2008-07-18")),
                new MovieInfo("abc",
                        "Dark Knight Rises",
                        2012,
                        List.of("Christian Bale", "Tom Hardy"),
                        LocalDate.parse("2012-07-20")));

        movieInfoRepository.saveAll(movieInfos)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll()
                .block();
    }

    @Test
    void addMovieInfo() {
        MovieInfo movieInfo = new MovieInfo(null,
                "Batman Begins1",
                2005,
                List.of("Christian Bale", "Michael Cane"),
                LocalDate.parse("2005-06-15"));

        webTestClient.post()
                .uri(MOVIE_INFO_URL)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(result -> assertEquals("Batman Begins1",
                        Objects.requireNonNull(result.getResponseBody())
                                .getName()));
    }

    @Test
    void addMovieInfo_validation() {
        MovieInfo movieInfo = new MovieInfo(null,
                "",
                -2005,
                List.of("Christian Bale", "Michael Cane"),
                LocalDate.parse("2005-06-15"));

        webTestClient.post()
                .uri(MOVIE_INFO_URL)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .consumeWith(result -> {
                    String responseBody = result.getResponseBody();
                    System.out.println(responseBody);
                    assert responseBody != null;
                    assertEquals("movieInfo.movieName should not be blank, movieInfo.year should not be Positive",
                            responseBody);
                });
    }

    @Test
    void getAllMovieInfos() {

        webTestClient.get()
                .uri(MOVIE_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }

    @Test
    void getMovieInfosByYear() {
        URI uri = UriComponentsBuilder.fromUriString(MOVIE_INFO_URL)
                .queryParam("year", 2005)
                .buildAndExpand()
                .toUri();
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(1);
    }

    @Test
    void getMovieInfosByName() {
        URI uri = UriComponentsBuilder.fromUriString(MOVIE_INFO_URL)
                .queryParam("movieName", "Batman Begins")
                .buildAndExpand()
                .toUri();
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(1);
    }

    @Test
    void getMovieInfoById() {

        String infoId = "abc";
        webTestClient.get()
                .uri(MOVIE_INFO_URL + "/{id}", infoId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(result -> assertEquals("Dark Knight Rises",
                        Objects.requireNonNull(result.getResponseBody())
                                .getName()));
    }

    @Test
    void getMovieInfoById_id404() {

        String infoId = "def";
        webTestClient.get()
                .uri(MOVIE_INFO_URL + "/{id}", infoId)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void updateMovieInfo() {
        MovieInfo movieInfo = new MovieInfo("abc",
                "Dark Knight Rises1",
                2022,
                List.of("Christian Bale", "Tom Hardy"),
                LocalDate.parse("2012-07-20"));


        webTestClient.put()
                .uri(MOVIE_INFO_URL + "/{id}", "abc")
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(result -> {
                    MovieInfo info = Objects.requireNonNull(result.getResponseBody());
                    assertEquals("Dark Knight Rises1", info.getName());
                    assertEquals(2022, info.getYear());
                });
    }

    @Test
    void updateMovieInfo_404() {
        MovieInfo movieInfo = new MovieInfo("def",
                "Dark Knight Rises1",
                2022,
                List.of("Christian Bale", "Tom Hardy"),
                LocalDate.parse("2012-07-20"));


        webTestClient.put()
                .uri(MOVIE_INFO_URL + "/{id}", "def")
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void deleteMovieInfoById() {

        String infoId = "abc";
        webTestClient.delete()
                .uri(MOVIE_INFO_URL + "/{id}", infoId)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(Void.class);
    }

    @Test
    void getAllMovieInfos_stream() {

        MovieInfo movieInfo = new MovieInfo(null,
                "Batman Begins1",
                2005,
                List.of("Christian Bale", "Michael Cane"),
                LocalDate.parse("2005-06-15"));

        webTestClient.post()
                .uri(MOVIE_INFO_URL)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(result -> assertEquals("Batman Begins1",
                        Objects.requireNonNull(result.getResponseBody())
                                .getName()));

        Flux<MovieInfo> responseBody = webTestClient.get()
                .uri(MOVIE_INFO_URL + "/stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(MovieInfo.class)
                .getResponseBody()
                .log();

        StepVerifier.create(responseBody)
                .assertNext(info -> {
                    assertEquals(info.getName(), movieInfo.getName());
                })
                .thenCancel()
                .verify();
    }
}
