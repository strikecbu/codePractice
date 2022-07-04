package com.learnkafka.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @NotNull(message = "Where is your id?")
    private Integer id;
    @NotBlank(message = "Should have book name")
    private String name;
    @NotBlank(message = "Should have an author")
    private String authorName;

}
