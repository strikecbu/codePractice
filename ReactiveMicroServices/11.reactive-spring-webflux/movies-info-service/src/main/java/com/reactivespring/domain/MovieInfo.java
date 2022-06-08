package com.reactivespring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfo {
    @Id
    private String movieInfoId;
    @NotBlank(message = "movieInfo.movieName should not be blank")
    private String name;
    @NotNull
    @Positive(message = "movieInfo.year should not be Positive")
    private Integer year;
    private List<String> cast;
    private LocalDate releaseDate;
}
