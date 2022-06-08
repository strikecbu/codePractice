package com.reactivespring.client;

import com.reactivespring.domain.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

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
                .bodyToFlux(Review.class).log();
    }
}
