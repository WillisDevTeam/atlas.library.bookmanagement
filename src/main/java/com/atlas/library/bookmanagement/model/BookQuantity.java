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
@Table(name = "book_quantity")
public class BookQuantity {
    @Id
    @Column(nullable = false)
    private String bookQuantityId;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private int totalQuantity;

    @Column(nullable = false)
    private int currentQuantity;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime modificationDate;
}
