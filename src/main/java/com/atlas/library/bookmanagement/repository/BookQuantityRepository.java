package com.atlas.library.bookmanagement.repository;

import com.atlas.library.bookmanagement.model.BookQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookQuantityRepository extends JpaRepository<BookQuantity, String> {

    Optional<BookQuantity> findByIsbn(String isbn);

}
