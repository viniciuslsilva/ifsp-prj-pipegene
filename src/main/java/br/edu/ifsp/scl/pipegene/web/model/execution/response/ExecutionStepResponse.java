package br.edu.ifsp.scl.pipegene.web.model.execution.response;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStep;
import br.edu.ifsp.scl.pipegene.web.model.provider.response.ProviderResponse;

import java.util.Map;
import java.util.UUID;

public class ExecutionStepResponse {
    private UUID id;
    private ProviderResponse provider;
    private String inputType;
    private String outputType;
    private String state;
    private Map<String, Object> params;

    public static ExecutionStepResponse createFromExecutionStep(ExecutionStep step) {
        return new ExecutionStepResponse(
                step.getId(),
                ProviderResponse.createWithPartialValuesFromProvider(step.getProvider()),
                step.getInputType(),
                step.getOutputType(),
                step.getState().name(),
                step.getParams()
        );
    }

    private ExecutionStepResponse() {
    }

    private ExecutionStepResponse(UUID id, ProviderResponse provider, String inputType, String outputType, String state,
                                  Map<String, Object> params) {
        this.id = id;
        this.provider = provider;
        this.inputType = inputType;
        this.outputType = outputType;
        this.state = state;
        this.params = params;
    }

    public UUID getId() {
        return id;
    }

    public ProviderResponse getProvider() {
        return provider;
    }

    public String getInputType() {
        return inputType;
    }

    public String getOutputType() {
        return outputType;
    }

    public String getState() {
        return state;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
