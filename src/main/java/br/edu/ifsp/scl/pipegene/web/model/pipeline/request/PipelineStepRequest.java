package br.edu.ifsp.scl.pipegene.web.model.pipeline.request;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.Map;
import java.util.UUID;

public class PipelineStepRequest {

    private UUID providerId;
    private String inputType;
    private String outputType;

    private Map<String, Object> params;

    public Provider convertToProvider() {
        return Provider.createWithIdAndInputOutputSupportedTypes(providerId, inputType, outputType);
    }

    public PipelineStepRequest() {
    }

    public PipelineStepRequest(UUID providerId, String inputType, String outputType, Map<String, Object> params) {
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
