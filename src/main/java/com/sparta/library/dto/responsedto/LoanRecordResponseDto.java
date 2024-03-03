package com.sparta.library.dto.responsedto;

import com.sparta.library.entity.Book;
import com.sparta.library.entity.Member;
import lombok.Getter;

@Getter
public class LoanRecordResponseDto {
    private String name;
    private String phone;
    private String title;
    private String author;

    public LoanRecordResponseDto(Member member, Book book) {
        this.name = member.getName();
        this.phone = member.getPhone();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }
}
