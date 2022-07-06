package com.learnkafka.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Book {
    @Id
    private Integer id;
    private String name;
    private String authorName;

    @OneToOne
    @JoinColumn(name = "eventId")
    private LibraryEvent libraryEvent;

}
