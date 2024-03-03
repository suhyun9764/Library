package com.sparta.library.service;

import com.sparta.library.dto.responsedto.LoanRecordResponseDto;
import com.sparta.library.entity.Book;
import com.sparta.library.entity.LoanRecord;
import com.sparta.library.entity.Member;
import com.sparta.library.exception.loan.MemberNotFoundException;
import com.sparta.library.repository.BookRepository;
import com.sparta.library.repository.LoanRecordRepository;
import com.sparta.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
