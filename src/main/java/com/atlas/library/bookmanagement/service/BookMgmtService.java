package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.User;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.repository.BookCheckoutRepository;
import com.atlas.library.bookmanagement.repository.BookRepository;
import com.atlas.library.bookmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookMgmtService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookCheckoutRepository bookCheckoutRepository;

    public Optional<Book> getLibraryBook(int bookId) {
        log.info("you are getting a book with bookId={}", bookId);
        return bookRepository.findById(bookId);
    }

    public Optional<Book> getLibraryBook(int bookId, String publisherName, String bookAuthor) {
        log.info("you are getting a book with bookId={}, publisherName={}, and author={}", bookId, publisherName, bookAuthor);
        if(publisherName.isEmpty() && bookAuthor.isEmpty()) {
            return bookRepository.findByBookIdAndPublisherNameAndAuthor(bookId, null, null);
        } else if (bookAuthor.isEmpty()){
            return bookRepository.findByBookIdAndPublisherNameAndAuthor(bookId, publisherName, null);
        } else if (publisherName.isEmpty()){
            return bookRepository.findByBookIdAndPublisherNameAndAuthor(bookId, null, bookAuthor);
        }
        return bookRepository.findByBookIdAndPublisherNameAndAuthor(bookId, publisherName, bookAuthor);
    }

    public Book createNewLibraryBook(Requests.CreateBookModel createBookModel) {
        // first check to see if book already exists in DB
        val bookAudit = bookRepository.findByBookIdAndISBN(createBookModel.getBookId(), createBookModel.getIsbn());
        if(bookAudit.isPresent()) {
            val existingBook = bookAudit.get();
            int currentNumberOfCopies = existingBook.getNumberOfCopies();
            log.info("Book with isbn={} already exists in DB, increasing numberOfCopies by one.", existingBook.getISBN());

            existingBook.setModificationDate(LocalDateTime.now());
            existingBook.setNumberOfCopies(currentNumberOfCopies+1);
            existingBook.setAvailable(true);

            return bookRepository.save(existingBook);
        }

        Book newBook = Requests.ofBookCreate(createBookModel);
        log.info("Creating a new book with bookId={} title={}, author={}, publisher={}", newBook.getBookId(), newBook.getTitle(), newBook.getAuthor(), newBook.getPublisherName());

        newBook.setAvailable(true);
        newBook.setNumberOfCopies(1);
        newBook.setModificationDate(LocalDateTime.now());
        newBook.setCreationDate(LocalDateTime.now());
        return bookRepository.save(newBook);
    }

    public Optional<Book> updateLibraryBook(int bookId, Double bookCost) {
        log.info("Attempting to update a book with bookId={}, cost={}", bookId, bookCost);
        val bookAudit = bookRepository.findById(bookId);
        if(bookAudit.isPresent()) {
            val existingBookToUpdate = bookAudit.get();
            log.info("Library book was found, attempting to update the book");
            existingBookToUpdate.setCost(bookCost);
            existingBookToUpdate.setModificationDate(LocalDateTime.now());
            return Optional.of(bookRepository.save(existingBookToUpdate));
        }
        return Optional.empty();
    }

    public void deleteLibraryBook(int bookId) {
        log.info("you are deleting a book with bookId={}", bookId);
        bookRepository.deleteById(bookId);
    }

    public Optional<User> getUser(int clientId) {
        log.info("you are getting a book with bookId={}", clientId);
        return userRepository.findById(clientId);
    }

    public User createNewUser(User user) {
        log.info("you are creating a new client with firstName={} lastName={}", user.getFirstName(), user.getLastName());
        return userRepository.save(user);
    }

    public Optional<User> updateUser(int clientId, User user) {
        log.info("you are attempting to update a book creating a new book with firstName={} lastName={}", user.getFirstName(), user.getLastName());
        if (getLibraryBook(clientId).isPresent()) {
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    public void deleteUser(int clientId) {
        log.info("you are deleting a client with clientId={}", clientId);
        userRepository.deleteById(clientId);
    }

    public Optional<BookCheckout> getBookCheckout(int bookCheckoutId) {
        log.info("you are getting a bookCheckout with bookCheckoutId={}", bookCheckoutId);
        return bookCheckoutRepository.findById(bookCheckoutId);
    }

    public Optional<BookCheckout> createBookCheckout(Requests.CreateBookCheckoutModel createBookCheckoutModel) {
        // first check to see if it exists in the DB
        Optional<BookCheckout> bookCheckoutAudit = bookCheckoutRepository.findById(createBookCheckoutModel.getBookCheckoutId());
        if(bookCheckoutAudit.isPresent()) {
            BookCheckout existingBookCheckout = bookCheckoutAudit.get();
            log.info("Book Checkout already exists in the db with bookCheckoutId={}. Returning existing book", existingBookCheckout.getBookCheckoutId());
            return Optional.empty();
        }

        BookCheckout newBookCheckout = Requests.ofBookCheckoutCreate(createBookCheckoutModel);
        log.info("Creating a new Book Checkout with bookCheckoutId={}, bookId={}, clientId={}", newBookCheckout.getBookCheckoutId(), newBookCheckout.getBookId(), newBookCheckout.getUserId());
        newBookCheckout.setRenewable(true);
        newBookCheckout.setCreationDate(LocalDateTime.now());
        newBookCheckout.setModificationDate(LocalDateTime.now());
        return Optional.of(bookCheckoutRepository.save(newBookCheckout));
    }

    public Optional<BookCheckout> updateBookCheckoutDueDate(int bookCheckoutId) {
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

    public void deleteBookCheckout(int bookCheckoutId) {
        log.info("Attempting to delete a bookCheckout with bookCheckoutId={}", bookCheckoutId);
        bookCheckoutRepository.deleteById(bookCheckoutId);
    }
}
