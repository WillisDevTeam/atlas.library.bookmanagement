package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.service.LibraryBookService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LibraryBookControllerTest {

    private LibraryBookController libraryBookController;
    private LibraryBookService libraryBookService;

    @BeforeEach
    public void init() {
        libraryBookService = mock(LibraryBookService.class);
        libraryBookController = new LibraryBookController(libraryBookService);
    }

    /*
     *  Unit Tests for getLibraryUser method
     */
    @Test
    public void testGetLibraryBookForSuccess() {
        // given:
        doReturn(getNewOptionalBookObject()).when(libraryBookService).getLibraryBook(anyString());

        // when:
        val response = libraryBookController.getLibraryBook("userId");

        // then:
        verify(libraryBookService, times(1)).getLibraryBook("userId");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *  Unit Tests for createLibraryUser method
     */
    @Test
    public void testCreateLibraryBookForSuccess() {
        // given:
        doReturn(getNewBookObject()).when(libraryBookService).createNewLibraryBook(any());

        // when:
        val response = libraryBookController.createLibraryBook(getNewCreateUserModel());

        // then:
        verify(libraryBookService, times(1)).createNewLibraryBook(getNewCreateUserModel());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *  Unit Tests for updateLibraryUserAccBal method
     */
    @Test
    public void testUpdateLibraryBookCostForSuccess() {
        // given:
        doReturn(getNewOptionalBookObject()).when(libraryBookService).updateLibraryBook(anyString(), anyDouble());

        // when:
        val response = libraryBookController.updateLibraryBookCost("userId", 1.00);

        // then:
        verify(libraryBookService, times(1)).updateLibraryBook("userId", 1.00);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *  Unit Tests for deleteLibraryUser method
     */
    @Test
    public void testDeleteLibraryBookForSuccess() {
        // when:
        val response = libraryBookController.deleteLibraryBook("bookId");

        // then:
        verify(libraryBookService, times(1)).deleteLibraryBook("bookId");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *   Helper methods
     */

    private Optional<Book> getNewOptionalBookObject() {
        return Optional.of(Book.builder()
                .bookId("bookId-001-TEST")
                .ISBN("ISBN-001-TEST")
                .title("BookTitle-001-TEST")
                .author("BookAuthor-001-TEST")
                .genre("BookGenre-001-TEST")
                .cost(10.00)
                .publisherName("BookPublisher-001-TEST")
                .publishDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .build());
    }

    private Book getNewBookObject() {
        return Book.builder()
                .bookId("bookId-002-TEST")
                .ISBN("ISBN-002-TEST")
                .title("BookTitle-002-TEST")
                .author("BookAuthor-002-TEST")
                .genre("BookGenre-002-TEST")
                .cost(5.00)
                .publisherName("BookPublisher-002-TEST")
                .publishDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .build();
    }

    private Requests.CreateBookModel getNewCreateUserModel() {
        return Requests.CreateBookModel.builder()
                .isbn("ISBN-002-TEST")
                .title("BookTitle-002-TEST")
                .author("BookAuthor-002-TEST")
                .genre("BookGenre-002-TEST")
                .cost(5.00)
                .publisherName("BookPublisher-002-TEST")
                .publishDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build();
    }
}
