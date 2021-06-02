package br.edu.ifsp.scl.pipegene.web.model.execution.response;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStep;

import java.util.UUID;

public class ExecutionStepResponse {
    private UUID stepId;
    private UUID providerId;
    private String inputType;
    private String outputType;
    private String state;

    public static ExecutionStepResponse createFromExecutionStep(ExecutionStep step) {
        return new ExecutionStepResponse(
                step.getStepId(),
                step.getProviderId(),
                step.getInputType(),
                step.getOutputType(),
                step.getState().name()
        );
    }

    private ExecutionStepResponse() {
    }

    private ExecutionStepResponse(UUID stepId, UUID providerId, String inputType, String outputType, String state) {
        this.stepId = stepId;
        this.providerId = providerId;
        this.inputType = inputType;
        this.outputType = outputType;
        this.state = state;
    }

    public UUID getStepId() {
        return stepId;
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

    public String getState() {
        return state;
    }
}
