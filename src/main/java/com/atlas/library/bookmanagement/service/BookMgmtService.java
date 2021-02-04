package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookMgmtService {

    private final BookRepository bookRepository;

    public Optional<Book> getLibraryBook(int bookId) {
        log.info("you are getting a book");
        return bookRepository.findById(bookId);
    }

    public Optional<BookCheckout> getBookCheckout(String bookCheckoutId) {
        return Optional.empty();
    }
}
