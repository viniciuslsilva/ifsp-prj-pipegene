package br.edu.ifsp.scl.pipegene.web.model.execution.request;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.UUID;

public class ExecutionStepRequest {

    private UUID providerId;
    private String inputType;
    private String outputType;

    public Provider convertToProvider() {
        return Provider.createWithPartialValues(providerId, inputType, outputType);
    }

    public ExecutionStepRequest() {
    }

    public ExecutionStepRequest(UUID providerId, String inputType, String outputType) {
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

    public UUID getProviderId() {
        return providerId;
    }

    public String getInputType() {
        return inputType;
    }

    public String getOutputType() {
        return outputType;
    }
}
