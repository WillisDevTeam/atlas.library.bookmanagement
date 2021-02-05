package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.User;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/atlas/library/mgmt/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryMgmtController {

    private final BookMgmtService bookService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getLibraryBook(@PathVariable("bookId") final String bookId) {
        val requestedBook = bookService.getLibraryBook(bookId);

        return (requestedBook.isPresent()) ? new ResponseEntity<>(requestedBook, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/books")
    public ResponseEntity<?> getLibraryBooks(@RequestParam(value = "title", required = false, defaultValue = "") List<String> title,
                                             @RequestParam(value = "bookAuthor", required = false, defaultValue = "") List<String> bookAuthor,
                                             @RequestParam(value = "genre", required = false, defaultValue = "") List<String> genre,
                                             @RequestParam(value = "publisherName", required = false, defaultValue = "") List<String> publisherName) {

        List<Book> requestedBooks = bookService.getLibraryBook(title, bookAuthor, genre, publisherName);

        return (!requestedBooks.isEmpty()) ? new ResponseEntity<>(requestedBooks, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TO DO: implement method to get books with filter

    @PostMapping("/book")
    public ResponseEntity<?> createLibraryBook(@RequestBody Requests.CreateBookModel createBookModel) {
        val newLibraryBook = bookService.createNewLibraryBook(createBookModel);

        return new ResponseEntity<>(newLibraryBook, HttpStatus.OK);
    }

    @PutMapping("/book/{bookId}/{bookCost}")
    public ResponseEntity<?> updateLibraryBook(@PathVariable("bookId") final String bookId, @PathVariable("bookCost") final double bookCost) {
        val requestedBook = bookService.updateLibraryBook(bookId, bookCost);
        return (requestedBook.isPresent()) ? new ResponseEntity<>(requestedBook, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<?> deleteLibraryBook(@PathVariable("bookId") final String bookId) {
        bookService.deleteLibraryBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLibraryUser(@PathVariable("userId") final String userId) {
        bookService.getUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/")
    public ResponseEntity<?> createLibraryUser(@RequestBody User user) {
        bookService.createNewUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateLibraryUser(@PathVariable("userId") final String userId, @RequestBody User user) {
        val updatedUser = bookService.updateUser(userId, user);
        return (updatedUser.isPresent()) ? new ResponseEntity<>(updatedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteLibraryUser(@PathVariable("userId") final String userId) {
        bookService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
