package com.atlas.library.bookmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CLIENT_ID")
    private int clientId;

    @Column(nullable = false, name = "FIRST_NAME")
    private String firstName;

    @Column(nullable = false, name = "LAST_NAME")
    private String lastName;

    @Column(name = "ACCOUNT_BALANCE")
    private double accountBalance;

    @Column(nullable = false, columnDefinition = "TIMESTAMP", name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP", name = "MODIFICATION_DATE")
    private LocalDateTime modificationDate;
}
