package com.clappartners.librairymanagement.repository;

import com.clappartners.librairymanagement.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
