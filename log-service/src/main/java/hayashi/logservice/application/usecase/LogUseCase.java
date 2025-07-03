package hayashi.logservice.application.usecase;

import hayashi.logservice.application.command.SaveLogCommand;
import hayashi.logservice.domain.model.LogDocument;

import java.util.List;

public interface LogUseCase {
    LogDocument save(SaveLogCommand command);

    List<LogDocument> saveBulk(List<SaveLogCommand> commands);
}
