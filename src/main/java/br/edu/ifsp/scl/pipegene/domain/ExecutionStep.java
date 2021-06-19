package br.edu.ifsp.scl.pipegene.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ExecutionStep {

    private UUID id;
    private UUID executionId;
    private Provider provider;
    private String inputType;
    private String outputType;
    private ExecutionStepState state;
    private Map<String, Object> params;
    private Integer stepNumber;

    private Pipeline pipeline;

    private ExecutionStep(UUID id, UUID executionId, Provider provider, String inputType, String outputType, ExecutionStepState state,
                          Map<String, Object> params, Integer stepNumber) {
        this.id = id;
        this.provider = provider;
        this.executionId = executionId;
        this.inputType = inputType;
        this.outputType = outputType;
        this.state = state;
        this.params = Collections.unmodifiableMap(params);
        this.stepNumber = stepNumber;
    }

    public static ExecutionStep createFromPipelineStep(PipelineStep p, UUID executionId) {
        return new ExecutionStep(
                UUID.randomUUID(),
                executionId,
                p.getProvider(),
                p.getInputType(),
                p.getOutputType(),
                ExecutionStepState.NOT_EXECUTED,
                p.getParams(),
                p.getStepNumber()
        );
    }

    public static ExecutionStep of(UUID id, UUID executionId, Provider providerId, String inputType, String outputType,
                                   ExecutionStepState state, Map<String, Object> params, Integer stepNumber) {
        return new ExecutionStep(id, executionId, providerId, inputType, outputType, state, params, stepNumber);
    }

    public UUID getId() {
        return id;
    }

    public Provider getProvider() {
        return provider;
    }

    public UUID getExecutionId() {
        return executionId;
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

    public Map<String, Object> getParams() {
        return params;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public Integer getStepNumber() {
        return stepNumber;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

}