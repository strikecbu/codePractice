package com.learnkafka.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class LibraryEvent {
    @Id
    @GeneratedValue
    private Integer eventId;

    @Enumerated(EnumType.STRING)
    private LibraryEventType eventType;

    @OneToOne(mappedBy = "libraryEvent", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Book book;

}
