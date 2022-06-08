package com.reactivespring.services;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService {
    private final MovieInfoRepository repository;

    public MovieInfoService(MovieInfoRepository movieInfoRepository) {
        this.repository = movieInfoRepository;
    }

    public Mono<MovieInfo> saveMovieInfo(MovieInfo movieInfo) {
        return repository.save(movieInfo);
    }

    public Flux<MovieInfo> getAllMovieInfos() {
        return repository.findAll();
    }

    public Mono<MovieInfo> getMovieInfoById(String id) {
        return repository.findById(id);
    }

    public Mono<MovieInfo> updateMovieInfo(MovieInfo updateMovieInfo, String id) {
        return repository.findById(id)
                .flatMap(movieInfo -> {
                    movieInfo.setName(updateMovieInfo.getName());
                    movieInfo.setYear(updateMovieInfo.getYear());
                    movieInfo.setCast(updateMovieInfo.getCast());
                    movieInfo.setReleaseDate(updateMovieInfo.getReleaseDate());
                    return repository.save(movieInfo);
                });
    }

    public Mono<Void> deleteMovieInfo(String id) {
        return repository.deleteById(id);
    }

    public Flux<MovieInfo> getMovieInfoByYear(Integer year) {
        return repository.findByYear(year);
    }

    public Mono<MovieInfo> getMovieInfoByName(String movieName) {
        return repository.findByName(movieName);
    }
}
