package cc.maids.lms.repositories;

import cc.maids.lms.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
