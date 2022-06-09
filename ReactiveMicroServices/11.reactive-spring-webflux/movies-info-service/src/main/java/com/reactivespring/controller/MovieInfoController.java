package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.services.MovieInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import javax.validation.Valid;

@Slf4j
@RestController
public class MovieInfoController {

    private final MovieInfoService movieInfoService;

    private final Sinks.Many<MovieInfo> infoReplay = Sinks.many()
            .replay()
            .all();

    public MovieInfoController(MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }

    @PostMapping("/v1/movieInfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody @Valid MovieInfo movieInfo) {
        return movieInfoService.saveMovieInfo(movieInfo)
                .doOnNext(infoReplay::tryEmitNext);
    }

    @GetMapping("/v1/movieInfos")
    public Flux<MovieInfo> getAllMovieInfos(@RequestParam(value = "year", required = false) Integer year,
                                            @RequestParam(value = "movieName", required = false) String movieName) {
        if (year != null) {
            return movieInfoService.getMovieInfoByYear(year);
        } else if (movieName != null) {
            log.info("Query movie name: {}", movieName);
            return Flux.from(movieInfoService.getMovieInfoByName(movieName))
                    .log();
        }
        return movieInfoService.getAllMovieInfos();
    }

    @GetMapping(value = "/v1/movieInfos/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MovieInfo> getAllMovieInfos() {
        return infoReplay.asFlux();
    }

    @GetMapping("/v1/movieInfos/{id}")
    public Mono<ResponseEntity<MovieInfo>> getMovieInfoById(@PathVariable String id) {
        return movieInfoService.getMovieInfoById(id)
                .map(movieInfo -> ResponseEntity.ok()
                        .body(movieInfo))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound()
                        .build()))
                .log();
    }

    @PutMapping("/v1/movieInfos/{id}")
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfo(@RequestBody MovieInfo movieInfo, @PathVariable String id) {
        return movieInfoService.updateMovieInfo(movieInfo, id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound()
                        .build()))
                .log();
    }

    @DeleteMapping("/v1/movieInfos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfoById(@PathVariable String id) {
        return movieInfoService.deleteMovieInfo(id)
                .log();
    }

}
