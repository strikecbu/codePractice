package com.reactivespring.client;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.exception.MoviesInfoClientException;
import com.reactivespring.exception.MoviesInfoServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoClient{

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
                .onStatus(HttpStatus::is4xxClientError, res -> {
                    HttpStatus httpStatus = res.statusCode();
                    if (httpStatus.equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new MoviesInfoClientException(
                                "Not found any movieInfo data from id: " + movieId,
                                httpStatus.value()));
                    }
                    return res.bodyToMono(String.class)
                            .flatMap(message -> Mono.error(new MoviesInfoClientException(message, httpStatus.value())));
                })
                .onStatus(HttpStatus::is5xxServerError, res -> res.bodyToMono(String.class)
                        .flatMap(message -> Mono.error(new MoviesInfoServerException(message))))
                .bodyToMono(MovieInfo.class)
                .log();
    }
}
