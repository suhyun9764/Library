package com.sparta.library.service;

import com.sparta.library.dto.responsedto.LoanRecordResponseDto;
import com.sparta.library.entity.Book;
import com.sparta.library.entity.LoanRecord;
import com.sparta.library.entity.Member;
import com.sparta.library.exception.book.BookNotFoundException;
import com.sparta.library.exception.loan.MemberNotFoundException;
import com.sparta.library.repository.BookRepository;
import com.sparta.library.repository.LoanRecordRepository;
import com.sparta.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

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
        checkBookId(bookId);
        Book book = getBook(bookId);
        boolean loanAvailable = book.isLoanAvailable();
        if (loanAvailable) {
            updateLoanRecord(memberId, bookId);
            updateBookLoanStatus(book);
            return "대출이 완료되었습니다";
        }
        return "이미 대출중인 책입니다";
    }

    private void checkMemberId(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty())
            throw new MemberNotFoundException("등록된 회원이 아닙니다");
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

    private void updateLoanRecord(Long memberId, Long bookId) {
        LoanRecord loanRecord = new LoanRecord(memberId, bookId);
        loanRecordRepository.save(loanRecord);
    }

    private void updateBookLoanStatus(Book book) {
        book.convertLoanStatus();
        bookRepository.save(book);
    }


}
