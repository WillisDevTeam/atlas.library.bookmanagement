package com.atlas.library.bookmanagement.model.web;

import com.atlas.library.bookmanagement.model.Book;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class Responses {

    @Data
    @Builder
    public static class CreateBookDetails {
        private int bookId;
        private String isbn;
        private String title;
        private String author;
        private String genre;
        private double cost;
        private String publisherName;
        private LocalDateTime publishDate;

        // figure out resource type and links
    }

    @Data
    @Builder
    public static class UpdateBookDetails {

        private int bookId;
        private String isbn;
        private String title;
        private String author;
        private String genre;
        private boolean available;
        private Double cost;
        private int numberOfCopies;
        private String publisherName;
        private LocalDateTime publishDate;
        private LocalDateTime creationDate;
        private LocalDateTime modificationDate;

        // figure out resource type and links
    }

    public static CreateBookDetails ofCreateDetails(Book book) {
        return CreateBookDetails.builder()
                .isbn(book.getISBN())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .cost(book.getCost())
                .publisherName(book.getPublisherName())
                .publishDate(book.getPublishDate())
                .build();
    }

    public static UpdateBookDetails ofUpdateDetails(Book book) {
        return UpdateBookDetails.builder()
                .bookId(book.getBookId())
                .cost(book.getCost())
                .available(book.isAvailable())
                .numberOfCopies(book.getNumberOfCopies())
                .build();
    }

}
