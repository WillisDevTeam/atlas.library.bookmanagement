package com.atlas.library.bookmanagement.repository;

import com.atlas.library.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
}
