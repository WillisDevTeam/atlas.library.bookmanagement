package com.atlas.library.bookmanagement.model.web;

import com.atlas.library.bookmanagement.model.Book;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


public class Requests {

    @Data
    @Builder
    public static class CreateBookModel {
        private int bookId;
        private String isbn;
        private String title;
        private String author;
        private String genre;
        private double cost;
        private String publisherName;
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime publishDate;
    }

    @Data
    @Builder
    public static class UpdateBookModel {
        private double cost;
    }

    @Data
    @Builder
    public static class ClientModel {
        private int clientId;
        private String firstName;
        private String lastName;
        private double accountBalance;
    }

    public static Book ofCreate(Requests.CreateBookModel createBookModel) {
        return Book.builder()
                .bookId(createBookModel.getBookId())
                .ISBN(createBookModel.getIsbn())
                .title(createBookModel.getTitle())
                .author(createBookModel.getAuthor())
                .genre(createBookModel.getGenre())
                .cost(createBookModel.getCost())
                .publisherName(createBookModel.getPublisherName())
                .publishDate(createBookModel.getPublishDate())
                .build();
    }
}
