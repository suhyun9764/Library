package com.sparta.library.service;

import com.sparta.library.dto.BookRequestDto;
import com.sparta.library.dto.BookResponseDto;
import com.sparta.library.entity.Book;
import com.sparta.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookResponseDto> findAll() {
        return bookRepository.findAllByOrderByRegisterDate().stream()
                .map(BookResponseDto::new).toList();
    }

    public BookResponseDto save(BookRequestDto bookRequestDto) {
        Book book = new Book(bookRequestDto);
        Book saveBook = bookRepository.save(book);
        return new BookResponseDto(saveBook);
    }
}
