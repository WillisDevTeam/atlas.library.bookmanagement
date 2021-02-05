package com.atlas.library.bookmanagement.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_checkout")
public class BookCheckout {

    @Id
    @Column(nullable = false)
    private String bookCheckoutId;

    @Column(nullable = false)
    private String bookId;

    @Column(nullable = false)
    private String userId;

    private boolean renewable;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dueDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime modificationDate;
}
