package com.sparta.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "loanRecord")
@NoArgsConstructor
public class LoanRecord extends LoanDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;
    @Column(name = "memberId", nullable = false)
    private Long memberId;
    @Column(name = "bookId", nullable = false)
    private Long bookId;
    @Column(name = "isReturn")
    private Boolean isReturn = false;
    @Column(name = "returnDate")
    private LocalDate returnDate;

    public LoanRecord(Long memberId, Long bookId) {
        this.memberId = memberId;
        this.bookId = bookId;
    }

    public void returnBook(LocalDate localDate) {
        isReturn = true;
        returnDate = localDate;
    }
}
