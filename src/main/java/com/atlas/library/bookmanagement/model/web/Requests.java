package com.atlas.library.bookmanagement.model.web;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class Requests {

    @Data
    @Builder
    public static class CreateBookModel {
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
    public static class CreateUserModel {
        private String firstName;
        private String lastName;
    }

    @Data
    @Builder
    public static class CreateBookCheckoutModel {
        private String bookId;
        private String userId;
        private LocalDateTime dueDate;
    }

    public static Book ofBookCreate(CreateBookModel createBookModel) {
        return Book.builder()
                .bookId(UUID.randomUUID().toString())
                .ISBN(createBookModel.getIsbn())
                .title(createBookModel.getTitle())
                .author(createBookModel.getAuthor())
                .genre(createBookModel.getGenre())
                .cost(createBookModel.getCost())
                .publisherName(createBookModel.getPublisherName())
                .publishDate(createBookModel.getPublishDate())
                .modificationDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .build();
    }

    public static BookCheckout ofBookCheckoutCreate(CreateBookCheckoutModel createBookCheckoutModel) {
        return BookCheckout.builder()
                .bookCheckoutId(UUID.randomUUID().toString())
                .bookId(createBookCheckoutModel.bookId)
                .userId(createBookCheckoutModel.userId)
                .dueDate(createBookCheckoutModel.getDueDate())
                .build();
    }

    public static User ofLibraryUserCreate(CreateUserModel createUserModel) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(createUserModel.getFirstName())
                .lastName(createUserModel.getLastName())
                .accountBalance(0.00)
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .build();
    }
}
