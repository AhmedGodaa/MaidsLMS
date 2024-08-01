package cc.maids.lms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cc.maids.lms.model.Book;
import cc.maids.lms.model.BorrowingRecord;
import cc.maids.lms.model.Patron;
import cc.maids.lms.services.BookService;
import cc.maids.lms.services.BorrowingRecordService;
import cc.maids.lms.services.PatronService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

@WebMvcTest(BorrowingRecordController.class)
public class BorrowingRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    @MockBean
    private BookService bookService;

    @MockBean
    private PatronService patronService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setId("1");
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setPublicationYear(2021);
        book.setIsbn("1234567890");

        patron = new Patron();
        patron.setId("1");
        patron.setName("Test Patron");
        patron.setContactInformation("test@example.com");

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId("1");
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());
    }

    @Test
    public void testBorrowBook() throws Exception {
        when(bookService.getBookById("1")).thenReturn(Optional.of(book));
        when(patronService.getPatronById("1")).thenReturn(Optional.of(patron));
        when(borrowingRecordService.addBorrowingRecord(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        mockMvc.perform(post("/api/borrow/1/patron/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.title").value(book.getTitle()))
                .andExpect(jsonPath("$.patron.name").value(patron.getName()));
    }

    @Test
    public void testReturnBook() throws Exception {
        when(borrowingRecordService.findBorrowingRecordByBookAndPatron(anyString(), anyString())).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordService.updateBorrowingRecord(anyString(), any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        mockMvc.perform(put("/api/return/1/patron/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.title").value(book.getTitle()))
                .andExpect(jsonPath("$.patron.name").value(patron.getName()));
    }
}
