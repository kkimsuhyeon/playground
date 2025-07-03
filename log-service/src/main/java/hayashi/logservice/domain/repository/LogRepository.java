package hayashi.logservice.domain.repository;

import hayashi.logservice.domain.model.LogDocument;

import java.util.List;

public interface LogRepository {
    LogDocument save(LogDocument log);

    List<LogDocument> saveAll(List<LogDocument> logs);
}
