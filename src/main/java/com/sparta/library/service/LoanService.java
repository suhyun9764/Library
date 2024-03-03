package com.sparta.library.service;

import com.sparta.library.dto.responsedto.LoanRecordResponseDto;
import com.sparta.library.entity.Book;
import com.sparta.library.entity.LoanRecord;
import com.sparta.library.entity.Member;
import com.sparta.library.exception.book.BookNotFoundException;
import com.sparta.library.exception.loan.ForbiddenLoanException;
import com.sparta.library.exception.member.MemberNotFoundException;
import com.sparta.library.repository.BookRepository;
import com.sparta.library.repository.LoanRecordRepository;
import com.sparta.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.library.constants.BookMessage.NOT_FOUND_BOOK;
import static com.sparta.library.constants.LoanMessage.*;
import static com.sparta.library.constants.MemberMessage.NOT_FOUND_MEMBER;

@Service
public class LoanService {
    private final MemberRepository memberRepository;
    private final LoanRecordRepository loanRecordRepository;
    private final BookRepository bookRepository;

    // public
    public LoanService(MemberRepository memberRepository, LoanRecordRepository loanRecordRepository, BookRepository bookRepository) {
        this.memberRepository = memberRepository;
        this.loanRecordRepository = loanRecordRepository;
        this.bookRepository = bookRepository;
    }

    public List<LoanRecordResponseDto> getMemberLoanHistory(Long memberId) {
        if (memberRepository.existsById(memberId)) {
            List<LoanRecord> loanHistoryList = loanRecordRepository.findAllByMemberIdAndIsReturnFalseOrderByLoanDate(memberId);

            List<LoanRecordResponseDto> loanRecordResponseDtoList = new ArrayList<>();
            for (LoanRecord loanRecord : loanHistoryList) {
                Member member = memberRepository.findById(memberId).get();
                Book book = bookRepository.findById(loanRecord.getBookId()).get();
                loanRecordResponseDtoList.add(new LoanRecordResponseDto(member, book));
            }
            return loanRecordResponseDtoList;
        }
        throw new MemberNotFoundException(NOT_FOUND_MEMBER);
    }

    public String executeBookLoan(Long memberId, Long bookId) {
        checkMemberId(memberId);
        checkPenalty(memberId);
        checkIfBookBorrowed(memberId);
        checkBookId(bookId);

        Book book = getBook(bookId);
        if (book.isLoanAvailable()) {
            addLoanRecord(memberId, bookId);
            updateBookLoanStatus(book);
            return COMPLETE_LOAN_BOOK;
        }
        return BORROWED_BOOK;
    }

    public String returnBook(Long bookId) {
        checkBookId(bookId);
        Book book = getBook(bookId);
        checkIsNotReturn(book);

        updateBookLoanStatus(book);
        Long recordId = updateLoanRecord(bookId);
        checkOverdue(recordId);

        return COMPLETE_RETURN_BOOK;

    }

    // private
    private void checkMemberId(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(NOT_FOUND_MEMBER);
        }
    }

    private void checkPenalty(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        LocalDate penaltyDate = member.getPenaltyDate();
        if (penaltyDate != null) {
            if (LocalDate.now().isBefore(penaltyDate))
                throw new ForbiddenLoanException(HAS_PENALTY + penaltyDate);
        }
    }

    private void checkIfBookBorrowed(Long memberId) {
        if (loanRecordRepository.existsByMemberIdAndIsReturnFalse(memberId)) {
            throw new ForbiddenLoanException(HAS_BORROWED_BOOK);
        }
    }

    private void checkBookId(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException(NOT_FOUND_BOOK);
        }
    }

    private Book getBook(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        Book findBook = book.orElseThrow();
        return findBook;
    }

    private void addLoanRecord(Long memberId, Long bookId) {
        LoanRecord loanRecord = new LoanRecord(memberId, bookId);
        loanRecordRepository.save(loanRecord);
    }

    private void updateBookLoanStatus(Book book) {
        book.convertLoanStatus();
        bookRepository.save(book);
    }

    private void checkIsNotReturn(Book book) {
        if (book.isLoanAvailable())
            throw new ForbiddenLoanException(NOT_BORROWED_BOOK);
    }

    private long updateLoanRecord(Long bookId) {
        LoanRecord loanRecord = getLoanRecord(bookId);
        loanRecord.returnBook(LocalDate.now());
        loanRecordRepository.save(loanRecord);
        return loanRecord.getRecordId();
    }

    private LoanRecord getLoanRecord(Long bookId) {
        List<LoanRecord> allByBookIdAndIsReturnFalse = loanRecordRepository.findAllByBookIdAndIsReturnFalse(bookId);
        LoanRecord loanRecord = allByBookIdAndIsReturnFalse.stream().
                findFirst().orElseThrow();

        return loanRecord;
    }


    private void checkOverdue(Long recordId) {
        LoanRecord loanRecord = loanRecordRepository.findById(recordId).get();
        if (loanRecord.isOverdue())
            givePenalty(loanRecord);
    }

    private void givePenalty(LoanRecord loanRecord) {
        Member member = memberRepository.findById(loanRecord.getMemberId()).get();
        member.getPenalty(loanRecord.getReturnDate());
        memberRepository.save(member);
    }


}
