package cc.maids.lms.services;

import cc.maids.lms.model.BorrowingRecord;
import cc.maids.lms.repositories.BorrowingRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;

    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll();
    }

    public Optional<BorrowingRecord> getBorrowingRecordById(String id) {
        return borrowingRecordRepository.findById(id);
    }

    public BorrowingRecord addBorrowingRecord(BorrowingRecord borrowingRecord) {
        return borrowingRecordRepository.save(borrowingRecord);
    }

    public BorrowingRecord updateBorrowingRecord(String id, BorrowingRecord recordDetails) {
        return borrowingRecordRepository.findById(id).map(record -> {
            record.setBook(recordDetails.getBook());
            record.setPatron(recordDetails.getPatron());
            record.setBorrowingDate(recordDetails.getBorrowingDate());
            record.setReturnDate(recordDetails.getReturnDate());
            return borrowingRecordRepository.save(record);
        }).orElseGet(() -> {
            recordDetails.setId(id);
            return borrowingRecordRepository.save(recordDetails);
        });
    }

    public void deleteBorrowingRecord(String id) {
        borrowingRecordRepository.deleteById(id);
    }

    public Optional<BorrowingRecord> findBorrowingRecordByBookAndPatron(String bookId, String patronId) {
        return borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);
    }
}
