package br.edu.ifsp.scl.pipegene.web.model.execution.request;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExecutionRequest {

    @NotNull
    private List<ExecutionStepRequest> executionSteps;
    @NotNull
    private UUID dataset;

    public ExecutionRequest() { }

    public ExecutionRequest(List<ExecutionStepRequest> executionSteps, UUID dataset) {
        this.executionSteps = executionSteps;
        this.dataset = dataset;
    }

    public List<ExecutionStepRequest> getExecutionSteps() {
        return new ArrayList<>(executionSteps);
    }

    public UUID getDataset() {
        return dataset;
    }
}
