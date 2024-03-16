package com.sparta.library.dto.responsedto;

import com.sparta.library.entity.Book;
import lombok.Getter;

import java.time.LocalDate;

import static com.sparta.library.constants.LoanMessage.CAN_LOAN;
import static com.sparta.library.constants.LoanMessage.CAN_NOT_LOAN;

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
            this.loanAvailableMessage = CAN_LOAN;
        else
            this.loanAvailableMessage = CAN_NOT_LOAN;
    }
}
