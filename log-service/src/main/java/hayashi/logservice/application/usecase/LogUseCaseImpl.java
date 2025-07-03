package hayashi.logservice.application.usecase;

import hayashi.logservice.application.command.SaveLogCommand;
import hayashi.logservice.application.factory.LogFactory;
import hayashi.logservice.domain.model.LogDocument;
import hayashi.logservice.domain.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogUseCaseImpl implements LogUseCase {

    private final LogFactory logFactory;
    private final LogRepository logRepository;

    @Override
    public LogDocument save(SaveLogCommand command) {
        LogDocument document = logFactory.toDocument(command);

        return logRepository.save(document);
    }

    @Override
    public List<LogDocument> saveBulk(List<SaveLogCommand> commands) {
        List<LogDocument> documents = commands.stream()
                .map(logFactory::toDocument)
                .toList();

        return logRepository.saveAll(documents);
    }
}
