package com.reactivespring.handler;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewReactorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class ReviewHandler {

    private final ReviewReactorRepository repository;

    public ReviewHandler(ReviewReactorRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAllReviews(ServerRequest req) {
        Optional<String> movieInfoId = req.queryParam("movieInfoId");
        if (movieInfoId.isPresent()) {
            Flux<Review> reviewFlux = repository.findByMovieInfoId(Long.parseLong(movieInfoId.get()));
            return responseFlux(reviewFlux);
        }
        Flux<Review> flux = repository.findAll();
        return responseFlux(flux);
    }

    private Mono<ServerResponse> responseFlux(Flux<Review> reviewFlux) {
        return ServerResponse.ok()
                .body(reviewFlux, Review.class);
    }

    public Mono<ServerResponse> addReview(ServerRequest req) {
        Mono<Review> mono = req.bodyToMono(Review.class)
                .flatMap(repository::save);
        return ServerResponse.status(HttpStatus.CREATED)
                .body(mono, Review.class);
    }

    public Mono<ServerResponse> updateReview(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return serverRequest.bodyToMono(Review.class)
                .flatMap(review -> repository.findById(id)
                        .flatMap(existReview -> {
                            existReview.setComment(review.getComment());
                            existReview.setRating(review.getRating());
                            return repository.save(existReview);
                        }))
                .flatMap(updatedReview -> ServerResponse.ok()
                        .bodyValue(updatedReview))
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }

    public Mono<ServerResponse> deleteReview(ServerRequest request) {
        String reviewId = request.pathVariable("id");
        return repository.findById(reviewId)
                .flatMap(review -> repository.delete(review)
                        .then(ServerResponse.noContent()
                                .build()))
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }
}
