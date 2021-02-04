package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.service.BookMgmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/atlas/library/checkout/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookCheckoutController {

    private final BookMgmtService bookService;

    @GetMapping("/{bookCheckoutId}")
    public ResponseEntity<?> getBookCheckout(@PathVariable("bookCheckoutId") final int bookCheckoutId) {
        Optional<BookCheckout> bookCheckout = bookService.getBookCheckout(bookCheckoutId);

        return (bookCheckout.isPresent()) ? new ResponseEntity<>(bookCheckout, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createBookCheckout(@RequestBody Requests.CreateBookCheckoutModel createBookCheckoutModel) {
        val newBookCheckout = bookService.createBookCheckout(createBookCheckoutModel);
        return (newBookCheckout.isPresent()) ? new ResponseEntity<>(newBookCheckout, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/{bookCheckoutId}")
    public ResponseEntity<?> updateBookCheckout(@PathVariable("bookId") final int bookCheckoutId) {
        log.info("Received a request to update the dueDate by seven days for bookCheckoutId={}", bookCheckoutId);
        val updatedBookCheckout = bookService.updateBookCheckoutDueDate(bookCheckoutId);

        return (updatedBookCheckout.isPresent()) ? new ResponseEntity<>(updatedBookCheckout, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{bookCheckoutId}")
    public ResponseEntity<?> deleteBookCheckout(@PathVariable("bookCheckoutId") final int bookCheckoutId) {
        log.info("Received a request to delete a bookCheckout with bookCheckoutId={}", bookCheckoutId);
        bookService.deleteBookCheckout(bookCheckoutId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
