package com.clappartners.librairymanagement.service;

import com.clappartners.librairymanagement.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author save(Author author);

    Optional<Author> findById(Long id);

    List<Author> findAll();

    void delete(Long id);

}