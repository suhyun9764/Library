package com.sparta.library.controller;

import com.sparta.library.dto.requestdto.BookRequestDto;
import com.sparta.library.dto.responsedto.BookResponseDto;
import com.sparta.library.service.BookService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findById(id));
    }
}
