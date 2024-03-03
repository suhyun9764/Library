package com.sparta.library.controller;

import com.sparta.library.dto.BookRequestDto;
import com.sparta.library.dto.BookResponseDto;
import com.sparta.library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    BookService bookService;

    @GetMapping("")
    public List<BookResponseDto> findAll() {
        return bookService.findAll();
    }

    @PostMapping("")
    public BookResponseDto save(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }
}
