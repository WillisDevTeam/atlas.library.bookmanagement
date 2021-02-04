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

@RestController
@RequestMapping("/atlas/library/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryMgmtController {

    private final BookMgmtService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getLibraryBook(@PathVariable("bookId") final String bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createLibraryBook(@RequestBody Book book) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateLibraryBook(@PathVariable("bookId") final String bookId, @RequestBody Book book) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteLibraryBook(@PathVariable("bookId") final String bookId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getLibraryClient(@PathVariable("clientId") final String clientId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createLibraryClient(@RequestBody Client client) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<?> updateLibraryClient(@PathVariable("clientId") final String clientId, @RequestBody Client client) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> deleteLibraryClient(@PathVariable("clientId") final String clientId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
