package com.clappartners.librairymanagement.service;

import com.clappartners.librairymanagement.entity.Book;
import com.clappartners.librairymanagement.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
    @Override
    public Book save(Book livre) {
        return bookRepository.save(livre);
    }
    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}