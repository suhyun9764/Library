package com.sparta.library.service;

import com.sparta.library.dto.responsedto.LoanRecordResponseDto;
import com.sparta.library.entity.Book;
import com.sparta.library.entity.LoanRecord;
import com.sparta.library.entity.Member;
import com.sparta.library.exception.book.BookNotFoundException;
import com.sparta.library.exception.member.MemberNotFoundException;
import com.sparta.library.exception.loan.ForbiddenLoanException;
import com.sparta.library.repository.BookRepository;
import com.sparta.library.repository.LoanRecordRepository;
import com.sparta.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final MemberRepository memberRepository;
    private final LoanRecordRepository loanRecordRepository;
    private final BookRepository bookRepository;

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
        throw new MemberNotFoundException("등록된 회원이 아닙니다");
    }

    public String executeBookLoan(Long memberId, Long bookId) {
        checkMemberId(memberId);
        checkPenalty(memberId);
        checkIfBookBorrowed(memberId);
        checkBookId(bookId);
        Book book = getBook(bookId);
        boolean loanAvailable = book.isLoanAvailable();
        if (loanAvailable) {
            addLoanRecord(memberId, bookId);
            updateBookLoanStatus(book);
            return "대출이 완료되었습니다";
        }
        return "이미 대출중인 책입니다";
    }

    private void checkIfBookBorrowed(Long memberId) {
        if(loanRecordRepository.existsByMemberIdAndIsReturnFalse(memberId)){
            throw new ForbiddenLoanException("현재 대출중인 도서가 있습니다");
        }
    }

    private void checkPenalty(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        LocalDate penaltyDate = member.getPenaltyDate();
        if(penaltyDate!=null) {
            if (LocalDate.now().isBefore(penaltyDate))
                throw new ForbiddenLoanException("패널티로 인해 대출이 불가합니다. 패널티 기간" + penaltyDate);
        }
    }

    private void checkMemberId(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty())
            throw new MemberNotFoundException("등록된 회원이 아닙니다");
    }

    private void addLoanRecord(Long memberId, Long bookId) {
        LoanRecord loanRecord = new LoanRecord(memberId, bookId);
        loanRecordRepository.save(loanRecord);
    }


    public String returnBook(Long bookId) {
        checkBookId(bookId);
        Book book = getBook(bookId);
        checkIsNotReturn(book);

        updateBookLoanStatus(book);
        Long recordId = updateLoanRecord(bookId);
        checkOverdue(recordId);

        return "반납이 완료되었습니다";

    }

    private void checkOverdue(Long recordId) {
        LoanRecord loanRecord = loanRecordRepository.findById(recordId).get();
        if(loanRecord.isOverdue())
            givePenalty(loanRecord);
    }

    private void givePenalty(LoanRecord loanRecord) {
        Member member = memberRepository.findById(loanRecord.getMemberId()).get();
        member.getPenalty(loanRecord.getReturnDate());
        memberRepository.save(member);
    }

    private long updateLoanRecord(Long bookId) {
        LoanRecord loanRecord = getLoanRecord(bookId);
        loanRecord.returnBook(LocalDate.now());
        loanRecordRepository.save(loanRecord);
        return loanRecord.getRecordId();
    }

    private LoanRecord getLoanRecord(Long bookId) {
        List<LoanRecord> allByBookIdAndIsReturnFalse = loanRecordRepository.findAllByBookIdAndIsReturnFalse(bookId);
        LoanRecord loanRecord = allByBookIdAndIsReturnFalse.stream().findFirst().get();
        return loanRecord;
    }

    private void checkIsNotReturn(Book book) {
        if(book.isLoanAvailable())
            throw new BookNotFoundException("대출하지 않은 책입니다");
    }

    private void checkBookId(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty())
            throw new BookNotFoundException("해당하는 도서가 존재하지 않습니다");
    }

    private Book getBook(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        Book findBook = book.get();
        return findBook;
    }


    private void updateBookLoanStatus(Book book) {
        book.convertLoanStatus();
        bookRepository.save(book);
    }



}
