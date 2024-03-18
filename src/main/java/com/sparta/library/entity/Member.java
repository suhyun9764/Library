package com.sparta.library.entity;

import com.sparta.library.dto.requestdto.MemberRequestDto;
import com.sparta.library.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Column(name = "socialNum", unique = true)
    private int socialNum;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "penalty")
    private LocalDate penaltyDate;


    public Member(MemberRequestDto memberRequestDto) {
        this.name = memberRequestDto.getName();
        this.gender = memberRequestDto.getGender();
        this.socialNum = memberRequestDto.getSocialNum();
        this.phone = memberRequestDto.getPhone();
        this.address = memberRequestDto.getAddress();
    }

    public void getPenalty(LocalDate returnDate){
        penaltyDate = returnDate.plusWeeks(2);
    }
}
