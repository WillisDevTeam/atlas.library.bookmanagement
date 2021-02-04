package com.atlas.library.bookmanagement.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_checkout")
public class BookCheckout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookCheckoutId;

    @Column(nullable = false)
    private int bookId;

    @Column(nullable = false)
    private int clientId;

    private boolean renewable;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private ZonedDateTime dueDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private ZonedDateTime creationDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private ZonedDateTime modificationDate;
}
