package br.edu.ifsp.scl.pipegene.web.model.execution.request;

import java.util.ArrayList;
import java.util.List;

public class ExecutionRequest {

    private List<ExecutionRequestFlowDetails> executionRequestFlowDetails;

    public ExecutionRequest() { }

    public ExecutionRequest(List<ExecutionRequestFlowDetails> executionRequestFlowDetails) {
        this.executionRequestFlowDetails = executionRequestFlowDetails;
    }

    public List<ExecutionRequestFlowDetails> getExecutionRequestFlowDetails() {
        return new ArrayList<>(executionRequestFlowDetails);
    }

    @Override
    public String toString() {
        return "ExecutionRequest{" +
                "executionRequestFlowDetails=" + executionRequestFlowDetails +
                '}';
    }
}
