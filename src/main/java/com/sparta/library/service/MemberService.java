package com.sparta.library.service;

import com.sparta.library.dto.requestdto.MemberRequestDto;
import com.sparta.library.dto.responsedto.MemberResponseDto;
import com.sparta.library.entity.Member;
import com.sparta.library.exception.member.MemberDuplicateException;
import com.sparta.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import static com.sparta.library.constants.MemberMessage.PHONE_NUMBER_DUPLICATE;
import static com.sparta.library.constants.MemberMessage.SOCIAL_NUMBER_DUPLICATE;

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
            throw new MemberDuplicateException(SOCIAL_NUMBER_DUPLICATE);
        }
        if(isPhoneDuplicated(memberRequestDto)){
            throw new MemberDuplicateException(PHONE_NUMBER_DUPLICATE);
        }
    }

    private boolean isPhoneDuplicated(MemberRequestDto memberRequestDto) {
        return memberRepository.existsByPhone(memberRequestDto.getPhone());
    }

    private boolean isSocialNumDuplicated(MemberRequestDto memberRequestDto) {
        return memberRepository.existsBySocialNum(memberRequestDto.getSocialNum());
    }
}
