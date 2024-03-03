package com.sparta.library.dto.requestdto;

import com.sparta.library.entity.enums.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookRequestDto {
    private String title;
    private String author;
    private Language language;
    private String publisher;
}
