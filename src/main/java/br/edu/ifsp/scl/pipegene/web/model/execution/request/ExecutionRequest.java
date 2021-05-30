package br.edu.ifsp.scl.pipegene.web.model.execution.request;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ExecutionRequest {

    @NotNull
    private List<ExecutionStepRequest> executionSteps;
    @NotNull
    private String dataset;

    public ExecutionRequest() { }

    public ExecutionRequest(List<ExecutionStepRequest> executionSteps, String dataset) {
        this.executionSteps = executionSteps;
        this.dataset = dataset;
    }

    public List<ExecutionStepRequest> getExecutionSteps() {
        return new ArrayList<>(executionSteps);
    }

    public String getDataset() {
        return dataset;
    }

    @Override
    public String toString() {
        return "ExecutionRequest{" +
                "executionRequestFlowDetails=" + executionSteps +
                '}';
    }
}
