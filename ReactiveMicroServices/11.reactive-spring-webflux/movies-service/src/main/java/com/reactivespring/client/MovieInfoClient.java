package com.reactivespring.client;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.exception.MoviesInfoClientException;
import com.reactivespring.exception.MoviesInfoServerException;
import com.reactivespring.util.RetryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
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
                .onStatus(HttpStatus::is5xxServerError, res -> {
                    HttpStatus status = res.statusCode();
                    log.info("Error Status: {}", status);
                    return res.bodyToMono(String.class)
                            .flatMap(message -> Mono.error(new MoviesInfoServerException(message)));
                })
                .bodyToMono(MovieInfo.class)
                .retryWhen(RetryUtil.getSpec())
                .log();
    }

    public Flux<MovieInfo> getMovieInfoFlux() {
        return webClient.get().uri(apiUrl.concat("/stream"))
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, res -> {
                    HttpStatus status = res.statusCode();
                    log.info("Error Status: {}", status);
                    return res.bodyToMono(String.class)
                            .flatMap(message -> Mono.error(new MoviesInfoServerException(message)));
                })
                .bodyToFlux(MovieInfo.class)
                .retryWhen(RetryUtil.getSpec());
    }
}
