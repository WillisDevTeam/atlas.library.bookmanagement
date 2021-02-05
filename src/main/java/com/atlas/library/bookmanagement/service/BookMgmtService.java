package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.configuration.query.QuerySpecificationsBuilder;
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
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookMgmtService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookCheckoutRepository bookCheckoutRepository;

    public Optional<Book> getLibraryBook(String bookId) {
        log.info("you are getting a book with bookId={}", bookId);
        return bookRepository.findById(bookId);
    }

    public List<Book> getLibraryBook(List<String> title, List<String> bookAuthor, List<String> genre, List<String> publisherName) {
        log.info("you are getting a book with titles={}, publisherNames={}, and authors={}", title.toString(), publisherName.toString(), bookAuthor.toString());

        val specification = new QuerySpecificationsBuilder<Book>()
                .with("title", title)
                .with("author", bookAuthor)
                .with("genre", genre)
                .with("publisherName", publisherName)
                .build();

        log.info("Searching for books by filters");
        return bookRepository.findAll(specification);
    }

    public Book createNewLibraryBook(Requests.CreateBookModel createBookModel) {
        log.info("Creating a new book with isbn={}, title={}, author={}, publisher={}", createBookModel.getIsbn(), createBookModel.getTitle(), createBookModel.getAuthor(), createBookModel.getPublisherName());
        return bookRepository.save(Requests.ofBookCreate(createBookModel));
    }

    public Optional<Book> updateLibraryBook(String bookId, Double bookCost) {
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

    public void deleteLibraryBook(String bookId) {
        log.info("you are deleting a book with bookId={}", bookId);
        bookRepository.deleteById(bookId);
    }

    public Optional<User> getUser(String userId) {
        log.info("you are getting a book with bookId={}", userId);
        return userRepository.findById(userId);
    }

    public User createNewUser(User user) {
        log.info("you are creating a new client with firstName={} lastName={}", user.getFirstName(), user.getLastName());
        return userRepository.save(user);
    }

    public Optional<User> updateUser(String userId, User user) {
        log.info("you are attempting to update a book creating a new book with firstName={} lastName={}", user.getFirstName(), user.getLastName());
        if (getLibraryBook(userId).isPresent()) {
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    public void deleteUser(String userId) {
        log.info("you are deleting a client with clientId={}", userId);
        userRepository.deleteById(userId);
    }

    public Optional<BookCheckout> getBookCheckout(int bookCheckoutId) {
        log.info("you are getting a bookCheckout with bookCheckoutId={}", bookCheckoutId);
        return bookCheckoutRepository.findById(bookCheckoutId);
    }

    public Optional<BookCheckout> getAllBookCheckout(int bookCheckoutId, int bookIds, int userIds) {
        log.info("you are getting a bookCheckout with bookCheckoutId={}", bookCheckoutId);

        // this requires a list of bookCheckout Objects
        // see if I can use that to optimize they way I get multiple books
        //
        // val bookTemp = bookCheckoutRepository.findAll(bookCheckoutId);

        val bookTemp = bookCheckoutRepository.findAllByBookIdAndUserId(bookCheckoutId, bookIds, userIds);
        if (bookTemp.isPresent()){
            val bookList = bookTemp.get();
            log.info("here is what was returned from bookTempSize={}", bookList.size());
            log.info("here is the contents of the book list={}", bookList.toString());
        }

        return bookCheckoutRepository.findById(bookCheckoutId);
    }

    public Optional<BookCheckout> createBookCheckout(Requests.CreateBookCheckoutModel createBookCheckoutModel) {

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
