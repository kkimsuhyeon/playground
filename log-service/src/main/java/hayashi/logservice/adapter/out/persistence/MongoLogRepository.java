package hayashi.logservice.adapter.out.persistence;

import hayashi.logservice.domain.model.LogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoLogRepository extends MongoRepository<LogDocument, String> {
}
