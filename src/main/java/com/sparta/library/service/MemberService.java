package com.sparta.library.service;

import com.sparta.library.dto.requestdto.MemberRequestDto;
import com.sparta.library.dto.responsedto.MemberResponseDto;
import com.sparta.library.entity.Member;
import com.sparta.library.exception.member.MemberDuplicateException;
import com.sparta.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponseDto save(MemberRequestDto memberRequestDto) {
        checkSaveAvailable(memberRequestDto);
        Member member = new Member(memberRequestDto);
            Member saveMember = memberRepository.save(member);
            return new MemberResponseDto(saveMember);

    }

    private void checkSaveAvailable(MemberRequestDto memberRequestDto) {
        if(isSocialNumDuplicated(memberRequestDto)){
            throw new MemberDuplicateException("이미 등록된 주민번호 입니다.");
        }
        if(isPhoneDuplicated(memberRequestDto)){
            throw new MemberDuplicateException("이미 등록된 전화번호 입니다.");
        }
    }

    private boolean isPhoneDuplicated(MemberRequestDto memberRequestDto) {
        return memberRepository.existsByPhone(memberRequestDto.getPhone());
    }

    private boolean isSocialNumDuplicated(MemberRequestDto memberRequestDto) {
        return memberRepository.existsBySocialNum(memberRequestDto.getSocialNum());
    }
}
