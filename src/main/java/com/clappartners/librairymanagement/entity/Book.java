package com.clappartners.librairymanagement.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "BOOKS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "BOOK_TITLE", length = 50)
    private String title;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_fk")
    private Author author;
    @Column(name = "BOOK_PRICE", length = 50)
    private double price;
    @Column(name = "BOOK_DATE_PUBLICATION", length = 50)
    private LocalDate dateOfPublication;
}
