package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.Client;
import com.atlas.library.bookmanagement.service.BookMgmtService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/atlas/library/mgmt/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryMgmtController {

    private final BookMgmtService bookService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getLibraryBook(@PathVariable("bookId") final int bookId) {
        Optional<Book> requestedBook = bookService.getLibraryBook(bookId);

        return new ResponseEntity<>(requestedBook, HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<?> createLibraryBook(@RequestBody Book book) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/book/{bookId}")
    public ResponseEntity<?> updateLibraryBook(@PathVariable("bookId") final String bookId, @RequestBody Book book) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<?> deleteLibraryBook(@PathVariable("bookId") final String bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getLibraryClient(@PathVariable("clientId") final String clientId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/client/")
    public ResponseEntity<?> createLibraryClient(@RequestBody Client client) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/client/{clientId}")
    public ResponseEntity<?> updateLibraryClient(@PathVariable("clientId") final String clientId, @RequestBody Client client) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/client/{clientId}")
    public ResponseEntity<?> deleteLibraryClient(@PathVariable("clientId") final String clientId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
