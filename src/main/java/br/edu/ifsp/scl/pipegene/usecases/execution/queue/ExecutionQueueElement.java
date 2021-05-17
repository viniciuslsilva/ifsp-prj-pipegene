package br.edu.ifsp.scl.pipegene.usecases.execution.queue;

import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequestFlowDetails;

import java.util.List;
import java.util.UUID;

public class ExecutionQueueElement {

    private UUID id;
    private List<ExecutionRequestFlowDetails> executionRequestFlowDetails;

    private ExecutionQueueElement() {}

    private ExecutionQueueElement(UUID id, List<ExecutionRequestFlowDetails> executionRequestFlowDetails) {
        this.id = id;
        this.executionRequestFlowDetails = executionRequestFlowDetails;
    }

    public static ExecutionQueueElement from(UUID id, ExecutionRequest executionRequest) {
        return new ExecutionQueueElement(id, executionRequest.getExecutionRequestFlowDetails());
    }

    public UUID getId() {
        return id;
    }
}
