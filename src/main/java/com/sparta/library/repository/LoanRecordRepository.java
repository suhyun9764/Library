package com.sparta.library.repository;

import com.sparta.library.entity.LoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRecordRepository extends JpaRepository<LoanRecord, Long> {
    List<LoanRecord> findAllByMemberIdAndIsReturnFalseOrderByLoanDate(Long memberId);
}
