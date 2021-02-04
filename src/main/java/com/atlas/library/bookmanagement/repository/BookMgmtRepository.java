package com.atlas.library.bookmanagement.repository;

import com.atlas.library.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMgmtRepository extends JpaRepository<Book, String> {
}
