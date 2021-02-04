package com.atlas.library.bookmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOK")
public class Book {

    @Id
    private int bookId;

    @Column(unique = true)
    private String ISBN;

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
    private LocalDateTime publishDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime modificationDate;
}
