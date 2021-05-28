package br.edu.ifsp.scl.pipegene.web.model.execution.request;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ExecutionRequest {

    @NotNull
    private List<ExecutionRequestFlowDetails> executionRequestFlowDetails;
    @NotNull
    private String dataset;

    public ExecutionRequest() { }

    public ExecutionRequest(List<ExecutionRequestFlowDetails> executionRequestFlowDetails, String dataset) {
        this.executionRequestFlowDetails = executionRequestFlowDetails;
        this.dataset = dataset;
    }

    public List<ExecutionRequestFlowDetails> getExecutionRequestFlowDetails() {
        return new ArrayList<>(executionRequestFlowDetails);
    }

    public String getDataset() {
        return dataset;
    }

    @Override
    public String toString() {
        return "ExecutionRequest{" +
                "executionRequestFlowDetails=" + executionRequestFlowDetails +
                '}';
    }
}
