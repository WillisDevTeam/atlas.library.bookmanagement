package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.Client;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.service.BookMgmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/atlas/library/mgmt/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryMgmtController {

    private final BookMgmtService bookService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getLibraryBook(@PathVariable("bookId") final int bookId) {
        val requestedBook = bookService.getLibraryBook(bookId);

        return (requestedBook.isPresent()) ? new ResponseEntity<>(requestedBook, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/books")
    public ResponseEntity<?> getLibraryBooks(@RequestParam(value = "books") List<Integer> bookIds,
                                             @RequestParam(value = "author", required = false, defaultValue = "") String author,
                                             @RequestParam(value = "publisherName", required = false, defaultValue = "") String publisherName) {

        List<Book> requestedBooks = new ArrayList<>();
        bookIds.forEach(bookId -> {
            val requestedBook = bookService.getLibraryBook(bookId, publisherName, author);
            requestedBook.ifPresent(requestedBooks::add);
            log.info("requestedBooks array size={}", requestedBooks.size());
        });

        return (!requestedBooks.isEmpty()) ? new ResponseEntity<>(requestedBooks, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TO DO: implement method to get books with filter

    @PostMapping("/book")
    public ResponseEntity<?> createLibraryBook(@RequestBody Requests.CreateBookModel createBookModel) {
        val newLibraryBook = bookService.createNewLibraryBook(createBookModel);

        return new ResponseEntity<>(newLibraryBook, HttpStatus.OK);
    }

    @PutMapping("/book/{bookId}/{bookCost}")
    public ResponseEntity<?> updateLibraryBook(@PathVariable("bookId") final int bookId, @PathVariable("bookCost") final double bookCost) {
        val requestedBook = bookService.updateLibraryBook(bookId, bookCost);
        return (requestedBook.isPresent()) ? new ResponseEntity<>(requestedBook, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<?> deleteLibraryBook(@PathVariable("bookId") final int bookId) {
        bookService.deleteLibraryBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getLibraryClient(@PathVariable("clientId") final int clientId) {
        bookService.getClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/client/")
    public ResponseEntity<?> createLibraryClient(@RequestBody Client client) {
        bookService.createNewClient(client);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/client/{clientId}")
    public ResponseEntity<?> updateLibraryClient(@PathVariable("clientId") final int clientId, @RequestBody Client client) {
        val updatedClient = bookService.updateClient(clientId, client);
        return (updatedClient.isPresent()) ? new ResponseEntity<>(updatedClient, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/client/{clientId}")
    public ResponseEntity<?> deleteLibraryClient(@PathVariable("clientId") final int clientId) {
        bookService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
