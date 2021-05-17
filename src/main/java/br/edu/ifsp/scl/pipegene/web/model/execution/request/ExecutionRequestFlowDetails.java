package br.edu.ifsp.scl.pipegene.web.model.execution.request;

import java.util.UUID;

public class ExecutionRequestFlowDetails {

    public UUID providerId;
    public String inputType;
    public String outputType;

    public ExecutionRequestFlowDetails() {
    }

    public ExecutionRequestFlowDetails(UUID providerId, String inputType, String outputType) {
        this.providerId = providerId;
        this.inputType = inputType;
        this.outputType = outputType;
    }


    @Override
    public String toString() {
        return "ExecutionRequestFlowDetails{" +
                "providerId=" + providerId +
                ", inputType='" + inputType + '\'' +
                ", outputType='" + outputType + '\'' +
                '}';
    }
}
