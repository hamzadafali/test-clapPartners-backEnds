package com.clappartners.librairymanagement.book;

import com.clappartners.librairymanagement.entity.Book;
import com.clappartners.librairymanagement.repository.BookRepository;
import com.clappartners.librairymanagement.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book livre1;
    private Book livre2;

    @BeforeEach
    void setUp() {

        livre1 = new Book();
        livre1.setId(1L);
        livre1.setTitle("Spring Boot in Action");
        livre1.setPrice(29.99);
        livre1.setDateOfPublication(LocalDate.of(2018, 1, 1));

        livre2 = new Book();
        livre2.setId(2L);
        livre2.setTitle("Java Concurrency in Practice");
        livre2.setPrice(39.99);
        livre2.setDateOfPublication(LocalDate.of(2015, 5, 20));
    }

    @Test
    void findAll() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(livre1, livre2));

        List<Book> livres = bookService.findAll();

        assertEquals(2, livres.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(livre1));

        Optional<Book> foundLivre = bookService.findById(1L);

        assertTrue(foundLivre.isPresent());
        assertEquals("Spring Boot in Action", foundLivre.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Book> foundLivre = bookService.findById(1L);

        assertFalse(foundLivre.isPresent());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void save() {
        when(bookRepository.save(livre1)).thenReturn(livre1);

        Book savedLivre = bookService.save(livre1);

        assertEquals(livre1, savedLivre);
        verify(bookRepository, times(1)).save(livre1);
    }

    @Test
    void deleteById() {
        doNothing().when(bookRepository).deleteById(1L);

        bookService.delete(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}