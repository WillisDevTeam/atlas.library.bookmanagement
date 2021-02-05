package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.service.BookCheckoutService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BookCheckoutControllerTest {

    private BookCheckoutController bookCheckoutController;
    private BookCheckoutService bookCheckoutService;

    @BeforeEach
    public void init() {
        bookCheckoutService = mock(BookCheckoutService.class);
        bookCheckoutController = new BookCheckoutController(bookCheckoutService);
    }

    /*
     *  Unit Tests for getLibraryUser method
     */
    @Test
    public void testGetBookCheckoutForSuccess() {
        // given:
        doReturn(getNewOptionalBookCheckoutObject()).when(bookCheckoutService).getBookCheckout(anyString());

        // when:
        val response = bookCheckoutController.getBookCheckout("bookCheckoutId");

        // then:
        verify(bookCheckoutService, times(1)).getBookCheckout("bookCheckoutId");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *  Unit Tests for createLibraryUser method
     */
    @Test
    public void testCreateBookCheckoutForSuccess() {
        // given:
        doReturn(getNewOptionalBookCheckoutObject()).when(bookCheckoutService).createBookCheckout(any());

        // when:
        val response = bookCheckoutController.createBookCheckout(getNewCreateBookCheckoutModel());

        // then:
        verify(bookCheckoutService, times(1)).createBookCheckout(getNewCreateBookCheckoutModel());
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *  Unit Tests for updateLibraryUserAccBal method
     */
    @Test
    public void testUpdateBookCheckoutForSuccess() {
        // given:
        doReturn(getNewOptionalBookCheckoutObject()).when(bookCheckoutService).updateBookCheckoutDueDate(anyString());

        // when:
        val response = bookCheckoutController.updateBookCheckout("bookCheckoutId");
        // then:
        verify(bookCheckoutService, times(1)).updateBookCheckoutDueDate("bookCheckoutId");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *  Unit Tests for deleteLibraryUser method
     */
    @Test
    public void testDeleteBookCheckoutForSuccess() {
        // given:
        doReturn(true).when(bookCheckoutService).deleteBookCheckout(anyString());

        // when:
        val response = bookCheckoutController.deleteBookCheckout("bookCheckoutId");

        // then:
        verify(bookCheckoutService, times(1)).deleteBookCheckout("bookCheckoutId");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     *   Helper methods
     */

    private Optional<BookCheckout> getNewOptionalBookCheckoutObject() {
        return Optional.of(BookCheckout.builder()
                .bookCheckoutId("bookCheckoutId-001-TEST")
                .bookId("bookId-001-TEST")
                .userId("userId-001-TEST")
                .renewable(true)
                .dueDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .build());
    }


    private BookCheckout getNewBookCheckoutObject() {
        return BookCheckout.builder()
                .bookCheckoutId("bookCheckoutId-002-TEST")
                .bookId("bookId-002-TEST")
                .userId("userId-002-TEST")
                .renewable(true)
                .dueDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .build();
    }

    private Requests.CreateBookCheckoutModel getNewCreateBookCheckoutModel() {
        return Requests.CreateBookCheckoutModel.builder()
                .bookId("bookId-002-TEST")
                .userId("userId-002-TEST")
                .dueDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build();
    }
}
