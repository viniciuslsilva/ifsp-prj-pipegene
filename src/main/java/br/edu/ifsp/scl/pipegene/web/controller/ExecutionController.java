package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.usecases.execution.ExecutionCRUD;
import br.edu.ifsp.scl.pipegene.usecases.execution.producer.SendExecutionsToProviders;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.CreateExecutionRequest;
import br.edu.ifsp.scl.pipegene.web.model.execution.response.ExecutionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@EnableAsync
@RequestMapping("api/v1/projects/{projectId}/executions")
@RestController
public class ExecutionController {

    private final ExecutionCRUD executionCRUD;
    private final SendExecutionsToProviders sendExecutionsToProviders;

    public ExecutionController(ExecutionCRUD executionCRUD, SendExecutionsToProviders sendExecutionsToProviders) {
        this.executionCRUD = executionCRUD;
        this.sendExecutionsToProviders = sendExecutionsToProviders;
    }

    @PostMapping
    public ResponseEntity<ExecutionResponse> addNewExecution(
            @PathVariable UUID projectId,
            @RequestBody @Valid CreateExecutionRequest request) {
        Execution execution = executionCRUD.addNewExecution(projectId, request);

        return ResponseEntity.ok(ExecutionResponse.createJustId(execution.getId()));
    }


    @GetMapping
    public ResponseEntity<List<ExecutionResponse>> listAllExecutions(@PathVariable UUID projectId) {
        List<Execution> executions = executionCRUD.listAllExecutions(projectId);

        return ResponseEntity.ok(
                executions.stream()
                        .map(ExecutionResponse::createFromExecution)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{executionId}")
    public ResponseEntity<ExecutionResponse> findExecutionById(
            @PathVariable UUID projectId,
            @PathVariable UUID executionId
    ) {
        Execution execution = executionCRUD.findExecutionById(projectId, executionId);

        return ResponseEntity.ok(ExecutionResponse.createFromExecution(execution));
    }

    @Async
    @Scheduled(cron = "${cron.expression}")
    public void sendExecutions() {
        sendExecutionsToProviders.send();
    }

}
