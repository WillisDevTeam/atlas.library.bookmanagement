package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.configuration.query.QuerySpecificationsBuilder;
import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.repository.BookCheckoutRepository;
import com.atlas.library.bookmanagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookCheckoutService {

    private final BookCheckoutRepository bookCheckoutRepository;
    private final BookRepository bookRepository;

    public Optional<BookCheckout> getBookCheckout(String bookCheckoutId) {
        log.info("you are getting a bookCheckout with bookCheckoutId={}", bookCheckoutId);
        return bookCheckoutRepository.findById(bookCheckoutId);
    }

    public List<BookCheckout> getAllBookCheckout(List<String> bookCheckoutIds, List<String> bookIds, List<String> userIds) {
        log.info("you are getting a book with titles={}, publisherNames={}, and authors={}", bookCheckoutIds.toString(), bookIds.toString(), userIds.toString());

        val specification = new QuerySpecificationsBuilder<BookCheckout>()
                .with("bookCheckoutId", bookCheckoutIds)
                .with("bookId", bookIds)
                .with("userId", userIds)
                .build();

        log.info("Searching for books by filters");
        return bookCheckoutRepository.findAll(specification);
    }

    public Optional<BookCheckout> createBookCheckout(Requests.CreateBookCheckoutModel createBookCheckoutModel) {

        BookCheckout newBookCheckout = Requests.ofBookCheckoutCreate(createBookCheckoutModel);
        log.info("Creating a new Book Checkout with bookCheckoutId={}, bookId={}, clientId={}", newBookCheckout.getBookCheckoutId(), newBookCheckout.getBookId(), newBookCheckout.getUserId());
        newBookCheckout.setRenewable(true);
        newBookCheckout.setCreationDate(LocalDateTime.now());
        newBookCheckout.setModificationDate(LocalDateTime.now());
        return Optional.of(bookCheckoutRepository.save(newBookCheckout));
    }

    public Optional<BookCheckout> updateBookCheckoutDueDate(String bookCheckoutId) {
        // first check to see if it exists in the DB
        Optional<BookCheckout> bookCheckoutAudit = bookCheckoutRepository.findById(bookCheckoutId);
        if(bookCheckoutAudit.isPresent() && bookCheckoutAudit.get().isRenewable()) {
            BookCheckout existingBookCheckout = bookCheckoutAudit.get();
            log.info("Successfully found Book Checkout in the db with bookCheckoutId={}. Attempting to Extend dueDate", existingBookCheckout.getBookCheckoutId());

            //perform logic to extend dueDate by 7 days and set renewable to false
            existingBookCheckout.setDueDate(existingBookCheckout.getDueDate().plusDays(7));
            existingBookCheckout.setRenewable(false);

            return Optional.of(bookCheckoutRepository.save(existingBookCheckout));
        } else if (bookCheckoutAudit.isPresent()) {
            BookCheckout existingBookCheckout = bookCheckoutAudit.get();
            log.info("BookCheckout has already been renewed for bookCheckoutId={}, bookId={}, clientId={}", existingBookCheckout.getBookCheckoutId(), existingBookCheckout.getBookId(), existingBookCheckout.getUserId());
            return Optional.of(existingBookCheckout);
        }

        return Optional.empty();
    }

    public void deleteBookCheckout(String bookCheckoutId) {
        //first get the book to be deleted
        val requestedBookForDeletion = bookCheckoutRepository.findById(bookCheckoutId);
        if(requestedBookForDeletion.isPresent()) {
            val reqBook = requestedBookForDeletion.get();
            log.info("Attempting to delete a bookCheckout with bookCheckoutId={}", bookCheckoutId);
            bookRepository.save(Book.builder()
                    .bookId(reqBook.getBookId())
                    .available(true)
                    .build());

            bookCheckoutRepository.deleteById(bookCheckoutId);
        }
        log.info("Attempting to delete a bookCheckout with bookCheckoutId={}", bookCheckoutId);



    }
}
