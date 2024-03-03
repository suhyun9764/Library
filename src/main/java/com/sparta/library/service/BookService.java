package com.sparta.library.service;

import com.sparta.library.exception.book.BookNotFoundException;
import com.sparta.library.dto.requestdto.BookRequestDto;
import com.sparta.library.dto.responsedto.BookResponseDto;
import com.sparta.library.entity.Book;
import com.sparta.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public BookResponseDto findById(Long id) {
        Optional<Book> findBook = bookRepository.findById(id);
        if(findBook.isPresent()){
            Book book = findBook.get();
            return new BookResponseDto(book);
        }
        throw new BookNotFoundException("해당하는 도서가 존재하지 않습니다");
    }
}
