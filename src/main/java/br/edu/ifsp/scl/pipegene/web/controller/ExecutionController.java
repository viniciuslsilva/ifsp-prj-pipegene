package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.usecases.execution.ExecutionService;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;
import br.edu.ifsp.scl.pipegene.web.model.execution.response.ExecutionResponse;
import br.edu.ifsp.scl.pipegene.web.model.execution.response.ProjectExecutionStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ExecutionController {

    private final ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping("v1/projects/{projectId}/executions")
    public ResponseEntity<ExecutionResponse> addNewExecution(
            @PathVariable UUID projectId,
            @RequestBody ExecutionRequest executionRequest) {
        UUID processId = executionService.addNewExecution(projectId, executionRequest);

        ExecutionResponse response = ExecutionResponse.builder()
                .requestId(UUID.randomUUID())
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
}
