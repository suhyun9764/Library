package com.sparta.library.controller;

import com.sparta.library.dto.MemberRequestDto;
import com.sparta.library.dto.MemberResponseDto;
import com.sparta.library.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    public ResponseEntity<MemberResponseDto> save(@RequestBody MemberRequestDto memberRequestDto){
            MemberResponseDto memberResponseDto = memberService.save(memberRequestDto);
            return ResponseEntity.ok(memberResponseDto);
    }
}
