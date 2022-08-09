package com.learnjava.apiclient;

import com.learnjava.domain.movie.Movie;
import com.learnjava.domain.movie.MovieInfo;
import com.learnjava.domain.movie.Review;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MovieClient {

    private final WebClient webClient;

    public MovieClient(WebClient webClient) {
        this.webClient = webClient;
    }

    private MovieInfo retrieveMovieInfo(Long movieInfoId) {
        return webClient
                .get()
                .uri("/v1/movie_infos/{movieInfoId}", movieInfoId)
                .retrieve()
                .bodyToMono(MovieInfo.class)
                .block();
    }

    private List<Review> retrieveReview(Long movieInfoId) {
        String uriString = UriComponentsBuilder.fromUriString("/v1/reviews")
                .queryParam("movieInfoId", movieInfoId)
                .buildAndExpand()
                .toUriString();

        return webClient
                .get()
                .uri(uriString)
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();
    }

    public Movie retrieveMovie(Long movieInfoId) {
        MovieInfo movieInfo = retrieveMovieInfo(movieInfoId);
        List<Review> reviews = retrieveReview(movieInfoId);
        return new Movie(movieInfo, reviews);
    }

    public CompletableFuture<Movie> retrieveMovie_CF(Long movieInfoId) {
        var movieInfo = CompletableFuture.supplyAsync(() -> retrieveMovieInfo(movieInfoId));
        var reviews = CompletableFuture.supplyAsync(() -> retrieveReview(movieInfoId));
        return movieInfo.thenCombine(reviews, Movie::new);
    }

    public List<Movie> retrieveMovies(List<Long> movieInfoIds) {
        return movieInfoIds.stream()
                .map(this::retrieveMovie)
                .collect(Collectors.toList());
    }

    public List<Movie> retrieveMovies_CF(List<Long> movieInfoIds) {
        return movieInfoIds.stream()
                .map(this::retrieveMovie_CF)
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public List<Movie> retrieveMovies_allOf(List<Long> movieInfoIds) {
        List<CompletableFuture<Movie>> futures = movieInfoIds.stream()
                .map(this::retrieveMovie_CF)
                .collect(Collectors.toList());
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allOf
                .thenApply(v ->
                        futures.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList())
                )
                .join();
    }
}
