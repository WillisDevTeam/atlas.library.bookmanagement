package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.Client;
import com.atlas.library.bookmanagement.service.BookMgmtService;
import lombok.RequiredArgsConstructor;
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

@Controller
@RequestMapping("/atlas/library/checkout/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookCheckoutController {

    private final BookMgmtService bookService;

    @GetMapping("/{bookCheckoutId}")
    public ResponseEntity<?> getBookCheckout(@PathVariable("bookCheckoutId") final String bookCheckoutId) {
        Optional<BookCheckout> bookCheckout = bookService.getBookCheckout(bookCheckoutId);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> createBookCheckout(@RequestBody Client client) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{bookCheckoutId}")
    public ResponseEntity<?> updateBookCheckout(@PathVariable("bookCheckoutId") final String bookCheckoutId, @RequestBody BookCheckout bookCheckout) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{bookCheckoutId}")
    public ResponseEntity<?> deleteBookCheckout(@PathVariable("bookCheckoutId") final String bookCheckoutId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
