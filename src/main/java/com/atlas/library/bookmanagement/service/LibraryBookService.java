package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.configuration.query.QuerySpecificationsBuilder;
import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookQuantity;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.repository.BookQuantityRepository;
import com.atlas.library.bookmanagement.repository.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryBookService {

    private final LibraryBookRepository libraryBookRepository;
    private final BookQuantityRepository bookQuantityRepository;

    public Optional<Book> getLibraryBook(String bookId) {
        log.info("you are getting a book with bookId={}", bookId);
        return libraryBookRepository.findById(bookId);
    }

    public List<Book> getAllLibraryBooks(List<String> bookIds, List<String> titles, List<String> bookAuthors, List<String> genres, List<String> publisherNames) {
        log.info("you are getting a book with titles={}, publisherNames={}, and authors={}", titles.toString(), publisherNames.toString(), bookAuthors.toString());

        val specification = new QuerySpecificationsBuilder<Book>()
                .with("bookId", bookIds)
                .with("title", titles)
                .with("author", bookAuthors)
                .with("genre", genres)
                .with("publisherName", publisherNames)
                .build();

        log.info("Searching for books by filters");
        return libraryBookRepository.findAll(specification);
    }

    @Transactional
    public Book createNewLibraryBook(Requests.CreateBookModel createBookModel) {

        // check book_quantity table for pre-existing record with same isbn
        Optional<BookQuantity> bookQtyResponseObj = bookQuantityRepository.findByIsbn(createBookModel.getIsbn());

        try {
            if (bookQtyResponseObj.isPresent()) {
                BookQuantity bookQty = bookQtyResponseObj.get();
                int totalQty = bookQty.getTotalQuantity();
                bookQty.setTotalQuantity(++totalQty);
                bookQty.setModificationDate(LocalDateTime.now());
                bookQuantityRepository.save(bookQty);
            } else {
                log.info("Book Quantity object was empty. creating a new Book Quantity Object");
                BookQuantity newBookQty = BookQuantity.builder()
                        .bookQuantityId(UUID.randomUUID().toString())
                        .isbn(createBookModel.getIsbn())
                        .totalQuantity(1)
                        .currentQuantity(1)
                        .creationDate(LocalDateTime.now())
                        .modificationDate(LocalDateTime.now())
                        .build();
                bookQuantityRepository.save(newBookQty);
            }

            log.info("Creating a new book with isbn={}, title={}, author={}, publisher={}", createBookModel.getIsbn(), createBookModel.getTitle(), createBookModel.getAuthor(), createBookModel.getPublisherName());
            return libraryBookRepository.save(Requests.ofBookCreate(createBookModel));
        } catch (Exception e) {
            log.error("Error occured while creating a new book", e);
            throw new RuntimeException(e);
        }
    }

    public Optional<Book> updateLibraryBook(String bookId, Double bookCost) {
        log.info("Attempting to update a book with bookId={}, cost={}", bookId, bookCost);
        val bookAudit = libraryBookRepository.findById(bookId);
        if(bookAudit.isPresent()) {
            val existingBookToUpdate = bookAudit.get();
            log.info("Library book was found, attempting to update the book");
            existingBookToUpdate.setCost(bookCost);
            existingBookToUpdate.setModificationDate(LocalDateTime.now());
            return Optional.of(libraryBookRepository.save(existingBookToUpdate));
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteLibraryBook(String bookId) {

        // get requested book details from table
        Optional<Book> requestedBookObj = libraryBookRepository.findById(bookId);

        if (requestedBookObj.isPresent()) {

            // delete book_quantity record from table after checking to make sure all copies have returned
            Optional<BookQuantity> bookQtyResponseObj = bookQuantityRepository.findByIsbn(requestedBookObj.get().getISBN());

            if (bookQtyResponseObj.isPresent() && bookQtyResponseObj.get().getCurrentQuantity() == bookQtyResponseObj.get().getTotalQuantity()) {
                try {
                    log.info("Attempting to delete a book with bookId={} and book quantity with bookQuantityId={}", bookId, bookQtyResponseObj.get().getBookQuantityId());
                    bookQuantityRepository.delete(bookQtyResponseObj.get());
                    libraryBookRepository.deleteById(bookId);
                } catch (Exception e) {
                    log.error("Error occured while deleting book and book quantity", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
