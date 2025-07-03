package hayashi.logservice.adapter.in.web;

import hayashi.logservice.adapter.in.web.reponse.LogResponse;
import hayashi.logservice.adapter.in.web.request.SaveRequest;
import hayashi.logservice.application.command.SaveLogCommand;
import hayashi.logservice.application.usecase.LogUseCase;
import hayashi.logservice.domain.model.LogDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/logs")
public class LogController {

    private final LogUseCase logUseCase;

    @PostMapping
    public ResponseEntity<LogResponse> save(@RequestBody SaveRequest request) {
        SaveLogCommand command = request.toCommand();
        LogDocument document = logUseCase.save(command);

        return ResponseEntity
                .ok()
                .body(LogResponse.from(document));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<LogResponse>> saveBulk(@RequestBody List<SaveRequest> requests) {
        List<SaveLogCommand> commands = requests.stream()
                .map(SaveRequest::toCommand)
                .toList();

        List<LogDocument> documents = logUseCase.saveBulk(commands);

        return ResponseEntity
                .ok()
                .body(documents.stream()
                        .map(LogResponse::from)
                        .toList());
    }
}
