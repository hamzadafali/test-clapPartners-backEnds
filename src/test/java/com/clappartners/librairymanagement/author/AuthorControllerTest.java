package com.clappartners.librairymanagement.author;

import com.clappartners.librairymanagement.controller.AuthorController;
import com.clappartners.librairymanagement.entity.Author;
import com.clappartners.librairymanagement.service.AuthorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorServiceImpl service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllAuthorsApi() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(new Author()));
        this.mockMvc.perform(get("/api/authors")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testCreateAuthorApi() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/authors")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"nom\":\"Martin\",\"prenom\":\"Robert\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testUpdateAuthorApi() throws Exception {

        Author existingAuthor = new Author();
        existingAuthor.setId(1L);
        existingAuthor.setFirstName("Old FirstName");
        existingAuthor.setLastName("Old LastName");

        Author updatedAuthor = new Author();
        updatedAuthor.setId(1L);
        updatedAuthor.setFirstName("Updated FirstName");
        updatedAuthor.setLastName("Upated LastName");

        when(service.findById(1L)).thenReturn(Optional.of(existingAuthor));
        when(service.save(any(Author.class))).thenReturn(updatedAuthor);

        mockMvc.perform(put("/api/authors/{id}", 1)
                        .content(objectMapper.writeValueAsString(updatedAuthor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Updated FirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Upated LastName"));

        verify(service, times(1)).findById(1L);
        verify(service, times(1)).save(any(Author.class));
    }

    @Test
    void deleteAuthorAPI() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/authors/{id}", 1))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findById(1L);
        verify(service, times(0)).delete(1L);
    }
}