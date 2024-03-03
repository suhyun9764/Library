package com.sparta.library.entity;

import com.sparta.library.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "gender",nullable = false)
    private Gender gender;
    @Column(name = "socialNum",nullable = false)
    private int socialNum;
    @Column(name = "phone",nullable = false)
    private String phone;
    @Column(name = "address", nullable = false)
    private String address;


}
