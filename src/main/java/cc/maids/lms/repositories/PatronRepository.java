package cc.maids.lms.repositories;

import cc.maids.lms.model.Patron;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatronRepository extends MongoRepository<Patron, String> {
}
