package com.sparta.library.dto;

import com.sparta.library.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String gender;
    private String phone;
    private String address;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.gender = member.getGender().getDisplayName();
        this.phone = member.getPhone();
        this.address = member.getAddress();
    }
}
