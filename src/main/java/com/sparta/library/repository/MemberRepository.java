package com.sparta.library.repository;

import com.sparta.library.entity.Book;
import com.sparta.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsBySocialNum(int socialNum);
    boolean existsByPhone(String phone);
}
