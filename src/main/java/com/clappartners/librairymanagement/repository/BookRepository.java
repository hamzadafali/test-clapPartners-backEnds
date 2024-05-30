package com.clappartners.librairymanagement.repository;

import com.clappartners.librairymanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
