 package com.reactivespring.controller;

import com.reactivespring.client.MovieInfoClient;
import com.reactivespring.client.MovieReviewClient;
import com.reactivespring.domain.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {

    private final MovieInfoClient infoClient;
    private final MovieReviewClient reviewClient;

    public MovieController(MovieInfoClient infoClient, MovieReviewClient reviewClient) {
        this.infoClient = infoClient;
        this.reviewClient = reviewClient;
    }


    @GetMapping("/{id}")
    public Mono<Movie> getMovieById(@PathVariable String id) {
        return infoClient.getMovieInfoById(id)
                .flatMap(info -> reviewClient.getReviewsByMovieId(id)
                        .collectList()
                        .flatMap(list -> Mono.just(new Movie(info, list))));
    }

    @GetMapping
    public Mono<String> hello() {
        return Mono.just("hello");
    }
}
