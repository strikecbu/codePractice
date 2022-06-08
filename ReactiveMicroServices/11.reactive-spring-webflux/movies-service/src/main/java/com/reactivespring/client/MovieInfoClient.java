package com.reactivespring.client;

import com.reactivespring.domain.MovieInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoClient {

    private final WebClient webClient;

    @Value("${apiUrl.movieInfo}")
    private String apiUrl;

    public MovieInfoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<MovieInfo> getMovieInfoById(String movieId) {
        String url = apiUrl.concat("/{id}");
        return webClient.get()
                .uri(url, movieId)
                .retrieve()
                .bodyToMono(MovieInfo.class).log();
    }
}
