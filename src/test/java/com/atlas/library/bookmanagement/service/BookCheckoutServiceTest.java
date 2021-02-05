package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.BookQuantity;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.repository.BookCheckoutRepository;
import com.atlas.library.bookmanagement.repository.BookQuantityRepository;
import com.atlas.library.bookmanagement.repository.LibraryBookRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BookCheckoutServiceTest {

    private BookCheckoutRepository bookCheckoutRepository;
    private LibraryBookRepository libraryBookRepository;
    private BookQuantityRepository bookQuantityRepository;
    private BookCheckoutService bookCheckoutService;

    @BeforeEach
    public void init() {
        bookCheckoutRepository = mock(BookCheckoutRepository.class);
        libraryBookRepository = mock(LibraryBookRepository.class);
        bookQuantityRepository = mock(BookQuantityRepository.class);
        bookCheckoutService = new BookCheckoutService(bookCheckoutRepository, libraryBookRepository, bookQuantityRepository);
    }

    /*
     *  Unit Tests for getBookCheckout method
     */
    @Test
    public void testGetBookCheckoutForSuccess() {
        // given:
        doReturn(getNewOptionalBookCheckoutObject()).when(bookCheckoutRepository).findById(anyString());

        // when:
        val response = bookCheckoutService.getBookCheckout("bookCheckoutId");

        // then:
        verify(bookCheckoutRepository, times(1)).findById("bookCheckoutId");
        assertNotNull(response);
        assertEquals(getNewOptionalBookCheckoutObject(), response);
    }

    /*
     *  Unit Tests for createBookCheckout method
     */
    @Test
    public void testCreateBookCheckoutForSuccess() {
        // given:
        doReturn(getNewOptionalBookObject()).when(libraryBookRepository).findById(anyString());
        doReturn(getNewOptionalBookQuantityObject()).when(bookQuantityRepository).findByIsbn(anyString());
        doReturn(getNewBookCheckoutObject()).when(bookCheckoutRepository).save(any());

        // when:
        val response = bookCheckoutService.createBookCheckout(getNewCreateBookCheckoutModel());

        // then:
        verify(libraryBookRepository, times(1)).findById("bookId-001-TEST");
        verify(bookQuantityRepository, times(1)).findByIsbn(anyString());
        verify(bookQuantityRepository, times(1)).save(any());
        verify(bookCheckoutRepository, times(1)).save(any());
        assertNotNull(response);
        assertEquals(getNewOptionalBookCheckoutObject(), response);
    }

    /*
     *  Unit Tests for updateBookCheckoutDueDate method
     */
    @Test
    public void testUpdateBookCheckoutDueDateForSuccess() {
        // given:
        doReturn(getNewOptionalBookCheckoutObject()).when(bookCheckoutRepository).findById(anyString());
        doReturn(getNewBookCheckoutObject()).when(bookCheckoutRepository).save(any());

        // when:
        val response = bookCheckoutService.updateBookCheckoutDueDate("bookCheckoutId");
        // then:
        verify(bookCheckoutRepository, times(1)).findById("bookCheckoutId");
        assertNotNull(response);
        assertEquals(getNewOptionalBookCheckoutObject(), response);
    }

    /*
     *  Unit Tests for deleteBookCheckout method
     */
    @Test
    public void testDeleteBookCheckoutForSuccess() {
        // given:
        doReturn(getNewOptionalBookCheckoutObject()).when(bookCheckoutRepository).findById(anyString());
        doReturn(getNewOptionalBookObject()).when(libraryBookRepository).findById(anyString());
        doReturn(getNewOptionalBookQuantityObject()).when(bookQuantityRepository).findByIsbn(anyString());

        // when:
        val response = bookCheckoutService.deleteBookCheckout("bookCheckoutId");

        // then:
        verify(bookCheckoutRepository, times(1)).findById("bookCheckoutId");
        verify(libraryBookRepository, times(1)).findById("bookId-001-TEST");
        verify(bookQuantityRepository, times(1)).findByIsbn("ISBN-001-TEST");
        verify(bookQuantityRepository, times(1)).save(any());
        verify(bookCheckoutRepository, times(1)).deleteById("bookCheckoutId");
        assertTrue(response);
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
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build());
    }


    private BookCheckout getNewBookCheckoutObject() {
        return BookCheckout.builder()
                .bookCheckoutId("bookCheckoutId-001-TEST")
                .bookId("bookId-001-TEST")
                .userId("userId-001-TEST")
                .renewable(true)
                .dueDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build();
    }

    private Requests.CreateBookCheckoutModel getNewCreateBookCheckoutModel() {
        return Requests.CreateBookCheckoutModel.builder()
                .bookId("bookId-001-TEST")
                .userId("userId-001-TEST")
                .dueDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build();
    }

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
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build());
    }

    private Optional<BookQuantity> getNewOptionalBookQuantityObject() {
        return Optional.of(BookQuantity.builder()
                .bookQuantityId("bookQuantityId-003-TEST")
                .isbn("ISBN-002-TEST")
                .totalQuantity(5)
                .currentQuantity(5)
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build());
    }
}
