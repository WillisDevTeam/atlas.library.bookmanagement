package com.atlas.library.bookmanagement.repository;

import com.atlas.library.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByISBN(String isbn);

    Optional<Book> findByBookIdAndTitleAndAuthor(int bookId, String title, String author);
}
