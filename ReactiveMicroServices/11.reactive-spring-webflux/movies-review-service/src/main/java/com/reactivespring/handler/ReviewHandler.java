package com.reactivespring.handler;

import com.reactivespring.domain.Review;
import com.reactivespring.exception.ReviewDataException;
import com.reactivespring.repository.ReviewReactorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReviewHandler {

    private final ReviewReactorRepository repository;

    private final Validator validator;

    private final Sinks.Many<Review> reviewSink = Sinks.many()
            .replay()
            .latest();

    public ReviewHandler(ReviewReactorRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Mono<ServerResponse> getAllReviews(ServerRequest req) {
        Optional<String> movieInfoId = req.queryParam("movieInfoId");
        if (movieInfoId.isPresent()) {
            Flux<Review> reviewFlux = repository.findByMovieInfoId(Long.parseLong(movieInfoId.get()));
            return reviewFlux
                    .collectList()
                    .filter(list -> list.size() > 0)
                    .flatMap(list -> ServerResponse.ok()
                            .bodyValue(list))
                    .switchIfEmpty(ServerResponse.notFound()
                            .build());
        }
        Flux<Review> flux = repository.findAll();
        return responseFlux(flux);
    }

    private Mono<ServerResponse> responseFlux(Flux<Review> reviewFlux) {
        return ServerResponse.ok()
                .body(reviewFlux, Review.class);
    }

    public Mono<ServerResponse> addReview(ServerRequest req) {
        return req.bodyToMono(Review.class)
                .doOnNext(this::validator)
                .flatMap(repository::save)
                .doOnNext(reviewSink::tryEmitNext)
                .flatMap(review -> ServerResponse.status(HttpStatus.CREATED)
                        .bodyValue(review));
//                .onErrorResume(ReviewDataException.class,
//                        e -> ServerResponse.badRequest()
//                                .bodyValue(e.getMessage()));
    }

    private void validator(Review review) {
        Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);
        if (constraintViolations.size() > 0) {
            String errorMessage = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .sorted()
                    .collect(Collectors.joining(", "));
            throw new ReviewDataException(errorMessage);
        }
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

    public Mono<ServerResponse> getReviewStream(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(reviewSink.asFlux(), Review.class);
    }

    public Mono<ServerResponse> getReviewStreamByInfoId(ServerRequest request) {
        Long movieInfoId = Long.parseLong(request.pathVariable("id"));
        Flux<Review> flux = reviewSink.asFlux()
                .filter(review -> review.getMovieInfoId().equals(movieInfoId));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(flux, Review.class);
    }
}
