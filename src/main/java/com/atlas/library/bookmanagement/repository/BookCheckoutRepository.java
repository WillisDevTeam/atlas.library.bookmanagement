package com.atlas.library.bookmanagement.repository;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookCheckout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCheckoutRepository extends JpaRepository<BookCheckout, String>, JpaSpecificationExecutor<BookCheckout> {
    Optional<BookCheckout> findByBookId(String bookId);
}
