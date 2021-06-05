package br.edu.ifsp.scl.pipegene.domain;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ExecutionStep {

    private UUID stepId;
    private UUID providerId;
    private String inputType;
    private String outputType;
    private ExecutionStepState state;
    private Map<String, Object> executionStepParams;


    private ExecutionStep(UUID stepId, UUID providerId, String inputType, String outputType, ExecutionStepState state,
                          Map<String, Object> executionStepParams) {
        this.stepId = stepId;
        this.providerId = providerId;
        this.inputType = inputType;
        this.outputType = outputType;
        this.state = state;
        this.executionStepParams = Collections.unmodifiableMap(executionStepParams);
    }

    public static ExecutionStep of(UUID stepId, UUID providerId, String inputType, String outputType,
                                   ExecutionStepState state, Map<String, Object> executionStepParams) {
        return new ExecutionStep(stepId, providerId, inputType, outputType, state, executionStepParams);
    }

    public static ExecutionStep createGeneratingStepId(UUID providerId, String inputType, String outputType, ExecutionStepState state,
                                                       Map<String, Object> executionStepParams) {
        return new ExecutionStep(UUID.randomUUID(), providerId, inputType, outputType, state,
                Objects.requireNonNull(executionStepParams));
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

    public Map<String, Object> getExecutionStepParams() {
        return executionStepParams;
    }
}