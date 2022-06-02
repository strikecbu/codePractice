package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.services.MovieInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = MovieInfoController.class)
@AutoConfigureWebTestClient
public class MovieInfoControllerUnitTest {

    private final String MOVIE_INFO_URL = "/v1/movieInfos";
    @MockBean
    private MovieInfoService movieInfoServiceMock;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getAllMovieInfos() {

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

//        given(movieInfoServiceMock
//                .getAllMovieInfos()).willReturn(Flux.fromIterable(movieInfos));

        when(movieInfoServiceMock.getAllMovieInfos()).thenReturn(Flux.fromIterable(movieInfos));

        webTestClient.get()
                .uri(MOVIE_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }

    @Test
    void getMovieInfoById() {
        var movieInfo = new MovieInfo("abc",
                "Dark Knight Rises",
                2012,
                List.of("Christian Bale", "Tom Hardy"),
                LocalDate.parse("2012-07-20"));

        given(movieInfoServiceMock
                .getMovieInfoById(any())).willReturn(Mono.just(movieInfo));

        String infoId = "abc";
        webTestClient.get()
                .uri(MOVIE_INFO_URL + "/{id}", infoId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(result -> assertEquals("Dark Knight Rises",
                        Objects.requireNonNull(result.getResponseBody())
                                .getMovieName()));
    }

    @Test
    void addMovieInfo() {
        MovieInfo movieInfo = new MovieInfo(null,
                "Batman Begins1",
                2005,
                List.of("Christian Bale", "Michael Cane"),
                LocalDate.parse("2005-06-15"));

        given(movieInfoServiceMock.saveMovieInfo(isA(MovieInfo.class))).willReturn(Mono.just(
                new MovieInfo("MockId",
                        "Batman Begins1",
                        2005,
                        List.of("Christian Bale", "Michael Cane"),
                        LocalDate.parse("2005-06-15"))));

        webTestClient.post()
                .uri(MOVIE_INFO_URL)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(result -> {
                    MovieInfo info = Objects.requireNonNull(result.getResponseBody());
                    assertEquals("Batman Begins1", info.getMovieName());
                    assertEquals("MockId", info.getMovieInfoId());
                });

    }

    @Test
    void updateMovieInfo() {
        MovieInfo movieInfo = new MovieInfo("abc",
                "Dark Knight Rises1",
                2022,
                List.of("Christian Bale", "Tom Hardy"),
                LocalDate.parse("2012-07-20"));

        given(movieInfoServiceMock.updateMovieInfo(isA(MovieInfo.class), isA(String.class))).willReturn(Mono.just(movieInfo));


        webTestClient.put()
                .uri(MOVIE_INFO_URL + "/{id}", "abc")
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(result -> {
                    MovieInfo info = Objects.requireNonNull(result.getResponseBody());
                    assertEquals("Dark Knight Rises1", info.getMovieName());
                    assertEquals(2022, info.getYear());
                });
    }

    @Test
    void deleteMovieInfoById() {

        given(movieInfoServiceMock.deleteMovieInfo(isA(String.class))).willReturn(Mono.empty());

        String infoId = "abc";
        webTestClient.delete()
                .uri(MOVIE_INFO_URL + "/{id}", infoId)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(Void.class);
    }

}
