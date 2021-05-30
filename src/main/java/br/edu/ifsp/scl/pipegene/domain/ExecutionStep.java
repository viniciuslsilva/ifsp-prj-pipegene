package br.edu.ifsp.scl.pipegene.domain;

import java.util.Objects;
import java.util.UUID;

public class ExecutionStep {

    private UUID stepId;
    private UUID providerId;
    private String inputType;
    private String outputType;
    private ExecutionStepState state;

    private ExecutionStep(UUID stepId, UUID providerId, String inputType, String outputType, ExecutionStepState state) {
        this.stepId = Objects.nonNull(stepId) ? stepId : UUID.randomUUID();
        this.providerId = providerId;
        this.inputType = inputType;
        this.outputType = outputType;
        this.state = state;
    }

    public static ExecutionStep of(UUID stepId, UUID providerId, String inputType, String outputType, ExecutionStepState state) {
        return new ExecutionStep(stepId, providerId, inputType, outputType, state);
    }

    public static ExecutionStep of(UUID providerId, String inputType, String outputType, ExecutionStepState state) {
        return new ExecutionStep(null, providerId, inputType, outputType, state);
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

    public ExecutionStepState getState() {
        return state;
    }

    public void setState(ExecutionStepState state) {
        this.state = state;
    }
}