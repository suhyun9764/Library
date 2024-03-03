package com.sparta.library.entity;

import com.sparta.library.dto.requestdto.BookRequestDto;
import com.sparta.library.entity.enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@NoArgsConstructor
@Getter
public class Book extends BookRegisterDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "language", nullable = false)
    private Language language;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "loanAvailable")
    private boolean loanAvailable = true;

    public Book(BookRequestDto bookRequestDto) {
        this.title = bookRequestDto.getTitle();
        this.author = bookRequestDto.getAuthor();
        this.language = bookRequestDto.getLanguage();
        this.publisher = bookRequestDto.getPublisher();
    }

    public void convertLoanStatus() {
        if (loanAvailable) {
            loanAvailable = false;
            return;
        }
        loanAvailable = true;
    }

}
