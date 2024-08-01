package cc.maids.lms.repositories;

import cc.maids.lms.model.BorrowingRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface BorrowingRecordRepository extends MongoRepository<BorrowingRecord, String> {
    Optional<BorrowingRecord> findByBookIdAndPatronId(String bookId, String patronId);
}