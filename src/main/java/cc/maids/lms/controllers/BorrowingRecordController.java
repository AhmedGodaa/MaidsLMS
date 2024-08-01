package cc.maids.lms.controllers;

import cc.maids.lms.model.Book;
import cc.maids.lms.model.BorrowingRecord;
import cc.maids.lms.model.Patron;
import cc.maids.lms.services.BookService;
import cc.maids.lms.services.BorrowingRecordService;
import cc.maids.lms.services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {
    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @Autowired
    private BookService bookService;

    @Autowired
    private PatronService patronService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable String bookId, @PathVariable String patronId) {
        Book book = bookService.getBookById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Patron patron = patronService.getPatronById(patronId).orElseThrow(() -> new RuntimeException("Patron not found"));

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowingDate(LocalDate.now());

        return ResponseEntity.ok(borrowingRecordService.addBorrowingRecord(record));
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable String bookId, @PathVariable String patronId) {
        BorrowingRecord record = borrowingRecordService.findBorrowingRecordByBookAndPatron(bookId, patronId)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found"));

        record.setReturnDate(LocalDate.now());

        return ResponseEntity.ok(borrowingRecordService.updateBorrowingRecord(record.getId(), record));
    }
}
