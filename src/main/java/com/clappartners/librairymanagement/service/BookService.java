package com.clappartners.librairymanagement.service;

import com.clappartners.librairymanagement.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    void delete(Long id);

}