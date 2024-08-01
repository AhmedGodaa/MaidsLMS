package cc.maids.lms.controllers;

import cc.maids.lms.config.utils.JwtUtils;
import cc.maids.lms.model.Book;
import cc.maids.lms.repositories.BookRepository;
import cc.maids.lms.repositories.BorrowingRecordRepository;
import cc.maids.lms.repositories.PatronRepository;
import cc.maids.lms.repositories.UserRepository;
import cc.maids.lms.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@WithMockUser(username = "user", roles = "USER")
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtils jwtUtils;  // Mocking JwtUtils

    @MockBean
    private UserRepository userRepository;  // Mocking UserRepository

    @MockBean
    private BorrowingRecordRepository borrowingRecordRepository;  // Mocking UserRepository

    @MockBean
    private PatronRepository patronRepository;  // Mocking UserRepository

    @MockBean
    private BookRepository bookRepository;  // Mocking UserRepository

    private Book book;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setId("1");
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setPublicationYear(2021);
        book.setIsbn("1234567890");

        UserDetails userDetails = User.withUsername("user")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")

    public void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(book.getTitle()));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")

    public void testGetBookById() throws Exception {
        when(bookService.getBookById("1")).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")

    public void testAddBook() throws Exception {
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")

    public void testUpdateBook() throws Exception {
        when(bookService.updateBook(any(String.class), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")

    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }





}
