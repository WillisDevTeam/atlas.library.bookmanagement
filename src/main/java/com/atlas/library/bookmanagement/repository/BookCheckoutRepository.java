package com.atlas.library.bookmanagement.repository;

import com.atlas.library.bookmanagement.model.BookCheckout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCheckoutRepository extends JpaRepository<BookCheckout, Integer> {

    @Query("select b from BookCheckout b where book_Checkout_id in :bookCheckoutId " +
            "and (:bookId is null or b.bookId = :bookId)" +
            "and (:userId is null or b.userId = :userId)")
    Optional<List<BookCheckout>> findAllByBookIdAndUserId(@Param("bookCheckoutId") int bookCheckoutId,
                                                          @Param("bookId") int bookId,
                                                          @Param("userId") int userId);



}
