package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntgTest {

    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movieInfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        movieInfoRepository.saveAll(movieInfos)
                .blockLast();
    }

    @Test
    public void testFindAll() {
        Flux<MovieInfo> movieInfoFlux = movieInfoRepository.findAll()
                .log();

        StepVerifier.create(movieInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void testFindById() {
        Mono<MovieInfo> movieInfoMono = movieInfoRepository.findById("abc")
                .log();

        StepVerifier.create(movieInfoMono)
                .assertNext(movieInfo -> {
                    assertEquals("Dark Knight Rises", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    public void testUpdate() {
        Optional<MovieInfo> info = movieInfoRepository.findById("abc")
                .blockOptional();
        assert (info.isPresent());
        MovieInfo movieInfo = info.get();
        movieInfo.setYear(2022);

        Mono<MovieInfo> movieInfoMono = movieInfoRepository.save(movieInfo);

        StepVerifier.create(movieInfoMono)
                .assertNext(mInfo -> {
                    assertEquals(2022, mInfo.getYear());
                })
                .verifyComplete();
    }

    @Test
    public void testSave() {
        MovieInfo movieInfo = new MovieInfo(null, "Batman Begins",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        Mono<MovieInfo> movieInfoMono = movieInfoRepository.save(movieInfo)
                .log();

        StepVerifier.create(movieInfoMono)
                .assertNext(mInfo -> {
                    assertNotNull(mInfo.getMovieInfoId());
                    assertEquals("Batman Begins", mInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    public void testDelete() {
        movieInfoRepository.deleteById("abc")
                .log()
                .block();

        Flux<MovieInfo> movieInfoFlux = movieInfoRepository.findAll()
                .log();

        StepVerifier.create(movieInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testFindByYear() {
        Flux<MovieInfo> flux = movieInfoRepository.findByYear(2005)
                .log();

        StepVerifier.create(flux)
                .expectNextCount(1)
                .verifyComplete();
    }
    @Test
    public void testFindByName() {
        String batman_begins = "Batman Begins";
        Mono<MovieInfo> mono = movieInfoRepository.findByName(batman_begins)
                .log();

        StepVerifier.create(mono)
                .assertNext(movieInfo -> {
                    assert batman_begins.equals(movieInfo.getName());
                })
                .verifyComplete();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll()
                .block();
    }
}
