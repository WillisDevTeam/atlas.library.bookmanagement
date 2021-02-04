package com.atlas.library.bookmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    @Column(unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    private String author;

    private String genre;

    @Column(nullable = false)
    private boolean available;

    private Double cost;

    private int numberOfCopies;

    private String publisherName;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private ZonedDateTime publishDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private ZonedDateTime creationDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private ZonedDateTime modificationDate;

}
