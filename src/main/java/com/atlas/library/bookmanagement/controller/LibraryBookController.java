package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookQuantity;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.service.LibraryBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/atlas/library/book/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryBookController {

    private final LibraryBookService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getLibraryBook(@PathVariable("bookId") final String bookId) {
        Optional<Book> requestedBook = bookService.getLibraryBook(bookId);

        if (requestedBook.isPresent()) {
            EntityModel<Book> resource = EntityModel.of(requestedBook.get());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateLibraryBookCost(bookId, 6.99)).withRel("Update book cost to Holiday Special price of $6.99"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteLibraryBook(bookId)).withRel("Delete Book"));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/filters")
    public ResponseEntity<?> getLibraryBooks(@RequestParam(value = "bookIds", required = false, defaultValue = "") List<String> bookIds,
                                             @RequestParam(value = "title", required = false, defaultValue = "") List<String> title,
                                             @RequestParam(value = "bookAuthor", required = false, defaultValue = "") List<String> bookAuthor,
                                             @RequestParam(value = "genre", required = false, defaultValue = "") List<String> genre,
                                             @RequestParam(value = "publisherName", required = false, defaultValue = "") List<String> publisherName) {

        List<Book> requestedBooksList = bookService.getAllLibraryBooks(bookIds, title, bookAuthor, genre, publisherName);

        if(!requestedBooksList.isEmpty()) {
            List<EntityModel<Book>> responseList = new ArrayList<>();

            requestedBooksList.stream().forEach(book -> {
                EntityModel<Book> resource = EntityModel.of(book);
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateLibraryBookCost(book.getBookId(), 6.99)).withRel("Update book cost to Holiday Special price of $6.99"));
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteLibraryBook(book.getBookId())).withRel("Delete Book"));
                responseList.add(resource);
            });

            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/availability/{bookId}")
    public ResponseEntity<?> checkBookAvailability(@PathVariable("bookId") final String bookId) {
        Optional<BookQuantity> requestedBookAvailability = bookService.getBookAvailability(bookId);

        if (requestedBookAvailability.isPresent()) {
            EntityModel<BookQuantity> resource = EntityModel.of(requestedBookAvailability.get());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateLibraryBookCost(bookId, 6.99)).withRel("Update book cost to Holiday Special price of $6.99"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteLibraryBook(bookId)).withRel("Delete Book"));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<?> createLibraryBook(@RequestBody Requests.CreateBookModel createBookModel) {
        Book newLibraryBook = bookService.createNewLibraryBook(createBookModel);

        EntityModel<Book> resource = EntityModel.of(newLibraryBook);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getLibraryBook(newLibraryBook.getBookId())).withRel("Get the new Book record"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateLibraryBookCost(newLibraryBook.getBookId(), 6.99)).withRel("Update book cost to Holiday Special price of $6.99"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteLibraryBook(newLibraryBook.getBookId())).withRel("Delete Book"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{bookId}/{bookCost}")
    public ResponseEntity<?> updateLibraryBookCost(@PathVariable("bookId") final String bookId, @PathVariable("bookCost") final double bookCost) {
        val requestedBook = bookService.updateLibraryBook(bookId, bookCost);

        if (requestedBook.isPresent()) {
            EntityModel<Book> resource = EntityModel.of(requestedBook.get());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getLibraryBook(bookId)).withRel("Get the new Book record"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteLibraryBook(bookId)).withRel("Delete Book"));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteLibraryBook(@PathVariable("bookId") final String bookId) {
        bookService.deleteLibraryBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
