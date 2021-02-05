package com.atlas.library.bookmanagement.controller;

import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.service.BookCheckoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/atlas/library/checkout/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookCheckoutController {

    private final BookCheckoutService bookService;

    @GetMapping("/{bookCheckoutId}")
    public ResponseEntity<?> getBookCheckout(@PathVariable("bookCheckoutId") final String bookCheckoutId) {
        Optional<BookCheckout> bookCheckout = bookService.getBookCheckout(bookCheckoutId);

        if (bookCheckout.isPresent()) {
            EntityModel<BookCheckout> resource = EntityModel.of(bookCheckout.get());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateBookCheckout(bookCheckoutId)).withRel("Extend due date by seven days"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteBookCheckout(bookCheckoutId)).withRel("Delete Book Checkout record"));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/bookCheckout")
    public ResponseEntity<?> getAllBookCheckout(@RequestParam(value = "bookCheckoutIds", required = false, defaultValue = "") List<String> bookCheckoutIds,
                                                @RequestParam(value = "bookIds", required = false, defaultValue = "") List<String> bookIds,
                                                @RequestParam(value = "userIds", required = false, defaultValue = "") List<String> userIds) {
        List<BookCheckout> bookCheckoutList = bookService.getAllBookCheckout(bookCheckoutIds,bookIds, userIds);

        if(!bookCheckoutList.isEmpty()) {
            List<EntityModel<BookCheckout>> responseList = new ArrayList<>();

            bookCheckoutList.stream().forEach(bookCheckout -> {
                EntityModel<BookCheckout> resource = EntityModel.of(bookCheckout);
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateBookCheckout(bookCheckout.getBookCheckoutId())).withRel("Extend due date by seven days"));
                resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteBookCheckout(bookCheckout.getBookCheckoutId())).withRel("Delete Book Checkout record"));
                responseList.add(resource);
            });

            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createBookCheckout(@RequestBody Requests.CreateBookCheckoutModel createBookCheckoutModel) {
        log.info("Recieved a request to create a new book checkout with bookId={}", createBookCheckoutModel.getBookId());
        val newBookCheckout = bookService.createBookCheckout(createBookCheckoutModel);

        if(newBookCheckout.isPresent()) {
            EntityModel<BookCheckout> resource = EntityModel.of(newBookCheckout.get());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getBookCheckout(newBookCheckout.get().getBookCheckoutId())).withRel("Get new Book Checkout record"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).updateBookCheckout(newBookCheckout.get().getBookCheckoutId())).withRel("Extend due date by seven days"));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{bookCheckoutId}")
    public ResponseEntity<?> updateBookCheckout(@PathVariable("bookCheckoutId") final String bookCheckoutId) {
        log.info("Received a request to update the dueDate by seven days for bookCheckoutId={}", bookCheckoutId);
        val updatedBookCheckout = bookService.updateBookCheckoutDueDate(bookCheckoutId);

        if (updatedBookCheckout.isPresent()) {
            EntityModel<BookCheckout> resource = EntityModel.of(updatedBookCheckout.get());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getBookCheckout(updatedBookCheckout.get().getBookCheckoutId())).withRel("Get new Book Checkout record"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteBookCheckout(bookCheckoutId)).withRel("Delete Book Checkout record"));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{bookCheckoutId}")
    public ResponseEntity<?> deleteBookCheckout(@PathVariable("bookCheckoutId") final String bookCheckoutId) {
        log.info("Received a request to delete a bookCheckout with bookCheckoutId={}", bookCheckoutId);
        val result = bookService.deleteBookCheckout(bookCheckoutId);
        return (result) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>("Resource was not found", HttpStatus.BAD_REQUEST);
    }
}
