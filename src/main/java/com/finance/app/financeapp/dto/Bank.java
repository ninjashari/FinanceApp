package com.finance.app.financeapp.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

/**
 * Represents a Bank entity in the application.
 * This entity is mapped to the "banks" table in the database.
 */
@Entity
@Table(name = "banks")
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String accountNumber;

    private String ifscCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
