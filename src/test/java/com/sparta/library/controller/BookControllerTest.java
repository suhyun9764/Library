package com.sparta.library.controller;

import com.sparta.library.dto.requestdto.BookRequestDto;
import com.sparta.library.dto.responsedto.BookResponseDto;
import com.sparta.library.entity.enums.Language;
import com.sparta.library.repository.BookRepository;
import com.sparta.library.service.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookControllerTest {

    @Autowired
    private BookRepository bookRepository;
    private BookService bookService = new BookService(bookRepository);

    @BeforeEach
    void before() {

    }

    @Test
    void findAll() {

    }

    @Test
    @Rollback(value = false)
    void save() {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("책 이름");
        bookRequestDto.setAuthor("작가 이름");
        bookRequestDto.setPublisher("출판사");
        bookRequestDto.setLanguage(Language.KOR);
        BookResponseDto save = bookService.save(bookRequestDto);
        Assertions.assertThat(save.getId()).isEqualTo(1L);

    }
}