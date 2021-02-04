package com.atlas.library.bookmanagement.repository;

import com.atlas.library.bookmanagement.model.BookCheckout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCheckoutRepository extends JpaRepository<BookCheckout, String> {
}
