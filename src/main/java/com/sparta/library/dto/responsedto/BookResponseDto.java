package com.sparta.library.dto.responsedto;

import com.sparta.library.entity.Book;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String language;
    private String publisher;
    private LocalDate registerDate;
    private String loanAvailableMessage;

    public BookResponseDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage().getDisplayName();
        this.publisher = book.getPublisher();
        this.registerDate = LocalDate.from(book.getRegisterDate());
        if (book.isLoanAvailable())
            this.loanAvailableMessage = "대출 가능";
        else
            this.loanAvailableMessage = "대출 불가";
    }
}
