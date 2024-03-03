package com.sparta.library.dto.requestdto;

import com.sparta.library.entity.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    private String name;
    private Gender gender;
    private int socialNum;
    private String phone;
    private String address;


}
