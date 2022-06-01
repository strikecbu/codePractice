package com.reactivespring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfo {
    @Id
    private String movieInfoId;
    private String movieName;
    private Integer year;
    private List<String> cast;
    private LocalDate comeInDate;
}
