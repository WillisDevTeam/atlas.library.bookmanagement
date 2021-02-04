package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.Client;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.repository.BookRepository;
import com.atlas.library.bookmanagement.repository.ClientRepository;
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
    private final ClientRepository clientRepository;

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
        val bookCheck = bookRepository.findByBookIdAndISBN(createBookModel.getBookId(), createBookModel.getIsbn());
        if((bookCheck).isPresent()) {
            val existingBook = bookCheck.get();
            int currentNumberOfCopies = existingBook.getNumberOfCopies();
            log.info("Book with isbn={} already exists in DB, increasing numberOfCopies by one.", existingBook.getISBN());

            existingBook.setModificationDate(LocalDateTime.now());
            existingBook.setNumberOfCopies(currentNumberOfCopies+1);
            existingBook.setAvailable(true);

            return bookRepository.save(existingBook);
        }

        log.info("Creating a new book with bookid = title={}", createBookModel.getTitle());
        val newBook = Requests.ofCreate(createBookModel);
        newBook.setAvailable(true);
        newBook.setNumberOfCopies(1);
        newBook.setModificationDate(LocalDateTime.now());
        newBook.setCreationDate(LocalDateTime.now());
        return bookRepository.save(newBook);
    }

    public Optional<Book> updateLibraryBook(int bookId, Double bookCost) {
        log.info("you are attempting to update a book with bookId={}, cost={}", bookId, bookCost);
        val bookCheck = bookRepository.findById(bookId);
        if((bookCheck).isPresent()) {
            val existingBookToUpdate = bookCheck.get();
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

    public Optional<Client> getClient(int clientId) {
        log.info("you are getting a book with bookId={}", clientId);
        return clientRepository.findById(clientId);
    }

    public Client createNewClient(Client client) {
        log.info("you are creating a new client with firstName={} lastName={}", client.getFirstName(), client.getLastName());
        return clientRepository.save(client);
    }

    public Optional<Client> updateClient(int clientId, Client client) {
        log.info("you are attempting to update a book creating a new book with firstName={} lastName={}", client.getFirstName(), client.getLastName());
        if (getLibraryBook(clientId).isPresent()) {
            return Optional.of(clientRepository.save(client));
        }
        return Optional.empty();
    }

    public void deleteClient(int clientId) {
        log.info("you are deleting a client with clientId={}", clientId);
        clientRepository.deleteById(clientId);
    }

    public Optional<BookCheckout> getBookCheckout(String bookCheckoutId) {
        return Optional.empty();
    }
}
