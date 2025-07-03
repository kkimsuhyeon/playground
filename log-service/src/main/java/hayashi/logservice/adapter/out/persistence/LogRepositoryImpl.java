package hayashi.logservice.adapter.out.persistence;

import hayashi.logservice.domain.model.LogDocument;
import hayashi.logservice.domain.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogRepositoryImpl implements LogRepository {

    private final MongoLogRepository mongoRepository;

    @Override
    public LogDocument save(LogDocument log) {
        return mongoRepository.save(log);
    }

    @Override
    public List<LogDocument> saveAll(List<LogDocument> logs) {
        return mongoRepository.saveAll(logs);
    }
}
