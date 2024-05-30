package com.clappartners.librairymanagement.book;

import com.clappartners.librairymanagement.controller.BookController;
import com.clappartners.librairymanagement.entity.Book;
import com.clappartners.librairymanagement.service.BookServiceImpl;
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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void greetingShouldReturnMessageFromService() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(new Book()));
        this.mockMvc.perform(get("/api/books")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void createItem() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/books")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"title\":\"Spring Ai In Action\"}")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void updateAuteurAPI() throws Exception {
        Book existingAuthor = new Book();
        existingAuthor.setId(1L);
        existingAuthor.setTitle("SpringBoot In Action");
        existingAuthor.setPrice(200);

        Book updatedAuthor = new Book();
        updatedAuthor.setId(1L);
        updatedAuthor.setPrice(500);
        updatedAuthor.setTitle("Maven In Action");

        when(service.findById(1L)).thenReturn(Optional.of(existingAuthor));
        when(service.save(any(Book.class))).thenReturn(updatedAuthor);

        mockMvc.perform(put("/api/books/{id}", 1)
                        .content(objectMapper.writeValueAsString(updatedAuthor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Maven In Action"));

        verify(service, times(1)).findById(1L);
        verify(service, times(1)).save(any(Book.class));
    }

    @Test
    void deleteAuteurNotFoundAPI() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/books/{id}", 1))
                .andExpect(status().isNotFound());

        verify(service, times(1)).findById(1L);
        verify(service, times(0)).delete(1L);
    }

}