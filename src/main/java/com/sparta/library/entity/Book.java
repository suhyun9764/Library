package com.sparta.library.entity;

import com.sparta.library.entity.enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "book")
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "language",nullable = false)
    private Language language;
    @Column(name = "publisher",nullable = false)
    private String publisher;
    @Column(name = "registrationDate",nullable = false)
    private LocalDate registrationDate;
}
