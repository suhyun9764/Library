package com.sparta.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name="loanRecord")
@NoArgsConstructor
public class LoanRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;
    @Column(name = "memberId",nullable = false)
    private Long memberId;
    @Column(name ="bookId",nullable = false)
    private Long bookId;
    @Column(name = "isReturn",columnDefinition = "boolean default false")
    private Boolean isReturn;
    @Column(name = "loanDate", nullable = false)
    private LocalDate loanDate;
    @Column(name = "returnDate")
    private LocalDate returnDate;

}
