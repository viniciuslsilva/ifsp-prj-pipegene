package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.usecases.execution.ExecutionService;
import br.edu.ifsp.scl.pipegene.usecases.execution.producer.SendExecutionsToProviders;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;
import br.edu.ifsp.scl.pipegene.web.model.execution.response.ExecutionResponse;
import br.edu.ifsp.scl.pipegene.web.model.execution.response.ProjectExecutionStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@EnableAsync
@RestController
public class ExecutionController {

    private final ExecutionService executionService;
    private final SendExecutionsToProviders sendExecutionsToProviders;

    public ExecutionController(ExecutionService executionService, SendExecutionsToProviders sendExecutionsToProviders) {
        this.executionService = executionService;
        this.sendExecutionsToProviders = sendExecutionsToProviders;
    }

    @PostMapping("v1/projects/{projectId}/executions")
    public ResponseEntity<ExecutionResponse> addNewExecution(
            @PathVariable UUID projectId,
            @RequestBody @Valid ExecutionRequest executionRequest) {
        UUID processId = executionService.addNewExecution(projectId, executionRequest);

        ExecutionResponse response = ExecutionResponse.builder()
                .processId(processId)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("v1/projects/{projectId}/executions/{executionId}")
    public ResponseEntity<?> checkProjectExecutionStatus(
            @PathVariable UUID projectId,
            @PathVariable UUID executionId
    ) {
        String status = executionService.checkProjectExecutionStatus(projectId, executionId);

        ProjectExecutionStatusResponse response = ProjectExecutionStatusResponse.builder()
                .id(UUID.randomUUID())
                .status(status)
                .build();

        return ResponseEntity.ok(response);
    }

    @Async
    @Scheduled(cron = "${cron.expression}")
    public void sendExecutions() {
        sendExecutionsToProviders.send();
    }

}
