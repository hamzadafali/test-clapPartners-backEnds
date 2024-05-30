package com.clappartners.librairymanagement.author;

import com.clappartners.librairymanagement.entity.Author;
import com.clappartners.librairymanagement.repository.AuthorRepository;
import com.clappartners.librairymanagement.service.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author auteur1;
    private Author auteur2;

    @BeforeEach
    void setUp() {
        auteur1 = new Author();
        auteur1.setId(1L);
        auteur1.setFirstName("Eric");
        auteur1.setLastName("John");

        auteur2 = new Author();
        auteur2.setId(2L);
        auteur2.setFirstName("Smith");
        auteur2.setLastName("Jane");
    }

    @Test
    void testFindAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(auteur1, auteur2));

        List<Author> auteurs = authorService.findAll();

        assertEquals(2, auteurs.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testFindAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(auteur1));

        Optional<Author> foundAuteur = authorService.findById(1L);

        assertTrue(foundAuteur.isPresent());
        assertEquals("Eric", foundAuteur.get().getFirstName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Author> foundAuteur = authorService.findById(1L);

        assertFalse(foundAuteur.isPresent());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveAuthor() {
        when(authorRepository.save(auteur1)).thenReturn(auteur1);

        Author savedAuteur = authorService.save(auteur1);

        assertEquals(auteur1, savedAuteur);
        verify(authorRepository, times(1)).save(auteur1);
    }

    @Test
    void testDeleteByIdAuthor() {
        doNothing().when(authorRepository).deleteById(1L);

        authorService.delete(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }
}