package br.edu.ifsp.scl.pipegene.web.model.execution.request;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.Map;
import java.util.UUID;

public class ExecutionStepRequest {

    private UUID providerId;
    private String inputType;
    private String outputType;

    private Map<String, Object> params;

    public Provider convertToProvider() {
        return Provider.createWithPartialValues(providerId, inputType, outputType);
    }

    public ExecutionStepRequest() {
    }

    public ExecutionStepRequest(UUID providerId, String inputType, String outputType, Map<String, Object> params) {
        this.providerId = providerId;
        this.inputType = inputType;
        this.outputType = outputType;
        this.params = params;
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

    public Map<String, Object> getParams() {
        return params;
    }
}
