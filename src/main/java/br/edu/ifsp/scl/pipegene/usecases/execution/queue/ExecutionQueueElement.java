package br.edu.ifsp.scl.pipegene.usecases.execution.queue;

import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionStepRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExecutionQueueElement {

    private UUID id;
    private List<ExecutionStepRequest> executionStepRequests;

    private ExecutionQueueElement() {}

    private ExecutionQueueElement(UUID id, List<ExecutionStepRequest> executionStepRequests) {
        this.id = id;
        this.executionStepRequests = executionStepRequests;
    }

    public static ExecutionQueueElement of(UUID id, ExecutionRequest executionRequest) {
        return new ExecutionQueueElement(id, executionRequest.getExecutionSteps());
    }

    public UUID getId() {
        return id;
    }

    public List<ExecutionStepRequest> getExecutionStepRequests() {
        return new ArrayList<>(executionStepRequests);
    }
}
