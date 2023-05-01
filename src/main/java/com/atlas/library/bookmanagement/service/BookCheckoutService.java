package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.configuration.query.QuerySpecificationsBuilder;
import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookCheckout;
import com.atlas.library.bookmanagement.model.BookQuantity;
import com.atlas.library.bookmanagement.model.User;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.repository.BookCheckoutRepository;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookCheckoutService {

    private final BookCheckoutRepository bookCheckoutRepository;
    private final LibraryBookRepository libraryBookRepository;
    private final BookQuantityRepository bookQuantityRepository;
    private final LibraryUserService libraryUserService;

    public Optional<BookCheckout> getBookCheckout(String bookCheckoutId) {
        log.info("Attempting to get a bookCheckout with bookCheckoutId={}", bookCheckoutId);
        return bookCheckoutRepository.findById(bookCheckoutId);
    }

    public List<BookCheckout> getAllBookCheckout(List<String> bookCheckoutIds, List<String> bookIds, List<String> userIds) {
        log.info("Attempting to get all books with bookCheckoutIds={}, bookIds={}, and userIds={}", bookCheckoutIds.toString(), bookIds.toString(), userIds.toString());

        val specification = new QuerySpecificationsBuilder<BookCheckout>()
                .with("bookCheckoutId", bookCheckoutIds)
                .with("bookId", bookIds)
                .with("userId", userIds)
                .build();

        log.info("Searching for books by filters");
        return bookCheckoutRepository.findAll(specification);
    }

    @Transactional
    public Optional<BookCheckout> createBookCheckout(Requests.CreateBookCheckoutModel createBookCheckoutModel) {

        BookCheckout newBookCheckout = Requests.ofBookCheckoutCreate(createBookCheckoutModel);
        Optional<BookQuantity> bookQuantityObj;
        Optional<Book> bookRequested = libraryBookRepository.findById(newBookCheckout.getBookId());

        if (bookRequested.isPresent()) {
            // check to see if that book is available for checkout
            bookQuantityObj = bookQuantityRepository.findByIsbn(bookRequested.get().getISBN());
            if (bookQuantityObj.isPresent() && bookQuantityObj.get().getCurrentQuantity() > 0) {

                try {
                    val bookQuantity = bookQuantityObj.get();
                    int currQty = bookQuantity.getCurrentQuantity();
                    bookQuantity.setCurrentQuantity(--currQty);
                    bookQuantity.setModificationDate(LocalDateTime.now());
                    bookQuantityRepository.save(bookQuantityObj.get());

                    newBookCheckout.setRenewable(true);
                    newBookCheckout.setCreationDate(LocalDateTime.now());
                    newBookCheckout.setModificationDate(LocalDateTime.now());

                    log.info("Creating a new Book Checkout with bookCheckoutId={}, bookId={}, userId={}", newBookCheckout.getBookCheckoutId(), newBookCheckout.getBookId(), newBookCheckout.getUserId());
                    return Optional.of(bookCheckoutRepository.save(newBookCheckout));

                } catch (Exception e) {
                    log.error("Error occurred while processing create checkout record", e);
                    throw new RuntimeException(e);
                }
            } else {
                log.info("Requested book was not available for checkout with bookId={}. Returning Empty", newBookCheckout.getBookId());
                return Optional.empty();
            }
        }

        log.info("Requested book was not available for checkout with bookId={}. Returning Empty", newBookCheckout.getBookId());
        return Optional.empty();
    }

    public Optional<BookCheckout> renewCheckout(Requests.RenewCheckoutModel renewCheckoutModel) {

        // look up the book checkout based on bookId, make sure the book is currently checked out
        Optional<BookCheckout> bookCheckoutOptional = bookCheckoutRepository.findByBookId(renewCheckoutModel.getBookId());

        if(bookCheckoutOptional.isPresent() && bookCheckoutOptional.get().getUserId().equals(renewCheckoutModel.getUserId())) {
            BookCheckout bookCheckout = bookCheckoutOptional.get();
            log.info("Here is the information on the book renewal: {}", bookCheckout);
            return updateBookCheckoutDueDate(bookCheckout.getBookCheckoutId());
        }

        log.info("Requested book was not available for checkout with bookId={}. Returning Empty", renewCheckoutModel.getBookId());
        return Optional.empty();
    }

    public Optional<BookCheckout> updateBookCheckoutDueDate(String bookCheckoutId) {
        // first check to see if it exists in the DB
        Optional<BookCheckout> bookCheckoutAudit = bookCheckoutRepository.findById(bookCheckoutId);

        if (bookCheckoutAudit.isPresent() && bookCheckoutAudit.get().isRenewable()) {
            BookCheckout existingBookCheckout = bookCheckoutAudit.get();
            log.info("Successfully found Book Checkout in the db with bookCheckoutId={}. Attempting to Extend dueDate", existingBookCheckout.getBookCheckoutId());

            //perform logic to extend dueDate by 7 days and set renewable to false
            existingBookCheckout.setDueDate(existingBookCheckout.getDueDate().plusDays(7));
            existingBookCheckout.setRenewable(false);

            return Optional.of(bookCheckoutRepository.save(existingBookCheckout));

        } else if (bookCheckoutAudit.isPresent()) {
            BookCheckout existingBookCheckout = bookCheckoutAudit.get();
            log.info("BookCheckout has already been renewed for bookCheckoutId={}, bookId={}, userId={}", existingBookCheckout.getBookCheckoutId(), existingBookCheckout.getBookId(), existingBookCheckout.getUserId());
            return Optional.of(existingBookCheckout);
        }

        return Optional.empty();
    }

    @Transactional
    public boolean deleteBookCheckout(String bookCheckoutId) {
        //first get the book checkout record to be deleted
        val requestedCheckoutRecord = bookCheckoutRepository.findById(bookCheckoutId);

        if (requestedCheckoutRecord.isPresent()) {
            val reqBook = requestedCheckoutRecord.get();

            Optional<Book> bookRequested = libraryBookRepository.findById(reqBook.getBookId());

            if (bookRequested.isPresent()){
                // check to see if that book is available for checkout
                Optional<BookQuantity> bookQuantityObj = bookQuantityRepository.findByIsbn(bookRequested.get().getISBN());

                if (bookQuantityObj.isPresent()) {
                    try {
                        val bookQty = bookQuantityObj.get();
                        int currQty = bookQty.getCurrentQuantity();
                        bookQty.setCurrentQuantity(++currQty);
                        bookQty.setModificationDate(LocalDateTime.now());
                        bookQuantityRepository.save(bookQty);

                        log.info("Attempting to delete a bookCheckout with bookCheckoutId={}", bookCheckoutId);
                        bookCheckoutRepository.deleteById(bookCheckoutId);
                        return true;
                    } catch (Exception e) {
                        log.error("Error occurred while processing delete checkout record", e);
                        throw new RuntimeException(e);
                    }
                } else {
                    log.info("Requested book was not found for bookId={}. Stopping process", requestedCheckoutRecord.get().getBookId());
                    return false;
                }
            } else {
                log.info("Requested book was not found for bookId={}. Stopping process", requestedCheckoutRecord.get().getBookId());
                return false;
            }
        }

        log.info("Requested checkout record was not found for bookCheckoutId={}. Stopping process", bookCheckoutId);
        return false;
    }

    public boolean chargeForOverDueBooks() {
        // get list of all books checked out
        log.info("[OVERDUE CHARGE] generating list of all over due books");
        try {
            // make query for only overdue books
            bookCheckoutRepository
                    .findAll()
                    .stream()
                    .filter(bookCheckout -> bookCheckout.getDueDate().isBefore(LocalDateTime.now()))
                    .forEach(this::chargeCustomerForOverDueBook);
        } catch(Exception e) {
            log.error("[OVERDUE CHARGE] exception occurred returning false", e);
            return false;
        }

        return true;
    }

    private void chargeCustomerForOverDueBook(BookCheckout overDueBook) {
        Optional<User> userOpt = libraryUserService.getUser(overDueBook.getUserId());

        if(userOpt.isPresent()) {
            User user = userOpt.get();
            log.info("[OVERDUE CHARGE] user: {} accountBalance before charge: {}", user.getUserId(), user.getAccountBalance());
            // charge user for overdue book
            double accountBalance = user.getAccountBalance()-2;
            log.info("[OVERDUE CHARGE] new accountBalance: {}", accountBalance);
            libraryUserService.updateUser(user.getUserId(),accountBalance);
        } else {
            log.error("[OVERDUE CHARGE] user no longer exists.");
        }
    }
}
