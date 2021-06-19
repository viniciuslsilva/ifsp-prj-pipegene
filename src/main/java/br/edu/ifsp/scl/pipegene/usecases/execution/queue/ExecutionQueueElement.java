package br.edu.ifsp.scl.pipegene.usecases.execution.queue;

import br.edu.ifsp.scl.pipegene.web.model.execution.request.CreateExecutionRequest;

import java.util.UUID;

public class ExecutionQueueElement {

    private UUID id;
    private CreateExecutionRequest executionRequest;

    private ExecutionQueueElement() {}

    private ExecutionQueueElement(UUID id, CreateExecutionRequest executionRequest) {
        this.id = id;
        this.executionRequest = executionRequest;
    }

    public static ExecutionQueueElement of(UUID id, CreateExecutionRequest request) {
        return new ExecutionQueueElement(id, request);
    }

    public UUID getId() {
        return id;
    }

    public CreateExecutionRequest getExecutionRequest() {
        return executionRequest;
    }
}
