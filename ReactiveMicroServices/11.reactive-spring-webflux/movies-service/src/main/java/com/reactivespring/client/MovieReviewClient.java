package com.reactivespring.client;

import com.reactivespring.domain.Review;
import com.reactivespring.exception.MoviesInfoClientException;
import com.reactivespring.exception.MoviesInfoServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class MovieReviewClient {

    private final WebClient webClient;

    @Value("${apiUrl.movieView}")
    private String apiUrl;

    public MovieReviewClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Review> getReviewsByMovieId(String movieId) {
        URI uri = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("movieInfoId", movieId)
                .buildAndExpand()
                .toUri();
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, res -> {
                    HttpStatus httpStatus = res.statusCode();
                    if (httpStatus.equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new MoviesInfoClientException(
                                "Not found any review data from id: " + movieId,
                                httpStatus.value()));
                    }
                    return res.bodyToMono(String.class)
                            .flatMap(message -> Mono.error(new MoviesInfoClientException(message, httpStatus.value())));
                })
                .onStatus(HttpStatus::is5xxServerError, res -> res.bodyToMono(String.class)
                        .flatMap(message -> Mono.error(new MoviesInfoServerException(message))))
                .bodyToFlux(Review.class).log();
    }
}
