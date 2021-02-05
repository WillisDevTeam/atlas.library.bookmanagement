package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.configuration.query.QuerySpecificationsBuilder;
import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.User;
import com.atlas.library.bookmanagement.model.web.Requests;
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
public class LibraryMgmtService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public Optional<Book> getLibraryBook(String bookId) {
        log.info("you are getting a book with bookId={}", bookId);
        return bookRepository.findById(bookId);
    }

    public List<Book> getLibraryBook(List<String> bookIds, List<String> titles, List<String> bookAuthors, List<String> genres, List<String> publisherNames) {
        log.info("you are getting a book with titles={}, publisherNames={}, and authors={}", titles.toString(), publisherNames.toString(), bookAuthors.toString());

        val specification = new QuerySpecificationsBuilder<Book>()
                .with("bookId", bookIds)
                .with("title", titles)
                .with("author", bookAuthors)
                .with("genre", genres)
                .with("publisherName", publisherNames)
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


}
