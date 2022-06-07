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
            Mono<Review> mono = repository.findById(movieInfoId.get());
            return ServerResponse.ok()
                    .body(mono, Review.class);
        }
        Flux<Review> flux = repository.findAll();
        return ServerResponse.ok()
                .body(flux, Review.class);
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
                .switchIfEmpty(ServerResponse.badRequest()
                        .bodyValue("Wrong id"));
    }

    public Mono<ServerResponse> deleteReview(ServerRequest request) {
        String reviewId = request.pathVariable("id");
        return repository.findById(reviewId)
                .flatMap(repository::delete)
                .flatMap(nothing -> ServerResponse.noContent()
                        .build())
                .switchIfEmpty(ServerResponse.badRequest()
                        .bodyValue("Wrong id"));
    }
}
