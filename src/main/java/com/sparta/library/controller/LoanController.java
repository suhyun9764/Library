package com.sparta.library.controller;

import com.sparta.library.dto.responsedto.LoanRecordResponseDto;
import com.sparta.library.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {
    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<LoanRecordResponseDto>> getMemberLoanHistory(@PathVariable Long memberId) {
        List<LoanRecordResponseDto> memberLoanHistory = loanService.getMemberLoanHistory(memberId);
        return ResponseEntity.ok(memberLoanHistory);
    }
}
