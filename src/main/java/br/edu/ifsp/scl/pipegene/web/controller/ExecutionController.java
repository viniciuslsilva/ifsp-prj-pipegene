package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.usecases.execution.ExecutionService;
import br.edu.ifsp.scl.pipegene.usecases.execution.producer.SendExecutionsToProviders;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;
import br.edu.ifsp.scl.pipegene.web.model.execution.response.ExecutionResponse;
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
        UUID executionId = executionService.addNewExecution(projectId, executionRequest);

        ExecutionResponse response = ExecutionResponse.builder()
                .executionId(executionId)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("v1/projects/{projectId}/executions/{executionId}")
    public ResponseEntity<?> findExecutionById(
            @PathVariable UUID projectId,
            @PathVariable UUID executionId
    ) {
        Execution response = executionService.findExecutionById(projectId, executionId);

        return ResponseEntity.ok(response);
    }

    @Async
    @Scheduled(cron = "${cron.expression}")
    public void sendExecutions() {
        sendExecutionsToProviders.send();
    }

}
