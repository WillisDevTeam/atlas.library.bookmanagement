package com.atlas.library.bookmanagement.repository;

import com.atlas.library.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByBookIdAndISBN(int bookId, String isbn);

    @Query("select b from Book b where book_id in :bookId and "
            + "(:publisherName is null or b.publisherName = :publisherName) and (:author is null or b.author = :author)")
    Optional<Book> findByBookIdAndPublisherNameAndAuthor(@Param("bookId") int bookId,
                                                 @Param("publisherName") String publisherName,
                                                 @Param("author") String author);
}
