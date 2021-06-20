package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.usecases.execution.ExecutionCRUD;
import br.edu.ifsp.scl.pipegene.web.model.execution.response.ExecutionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users/{userId}/executions")
public class UserExecutionController {

    private final ExecutionCRUD executionCRUD;

    public UserExecutionController(ExecutionCRUD executionCRUD) {
        this.executionCRUD = executionCRUD;
    }

    @GetMapping("")
    public ResponseEntity<List<ExecutionResponse>> listAllExecutions(@PathVariable UUID userId) {
        List<Execution> executions = executionCRUD.listAllExecutionByUserId(userId);

        return ResponseEntity.ok(
                executions.stream()
                        .map(ExecutionResponse::createFromExecution)
                        .collect(Collectors.toList())
        );
    }

}
