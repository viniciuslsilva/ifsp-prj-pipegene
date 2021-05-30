package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStep;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStepState;

import java.util.UUID;

public class ExecutionStepEntity {

    private UUID stepId;
    private UUID providerId;
    private String inputType;
    private String outputType;
    private String state;


    public ExecutionStep toExecutionStep() {
        return ExecutionStep.of(stepId, providerId, inputType, outputType, ExecutionStepState.valueOf(state));
    }

    private ExecutionStepEntity(UUID stepId, UUID providerId, String inputType, String outputType, String state) {
        this.stepId = stepId;
        this.providerId = providerId;
        this.inputType = inputType;
        this.outputType = outputType;
        this.state = state;
    }

    public static ExecutionStepEntity of(ExecutionStep executionStep) {
        return new ExecutionStepEntity(
                executionStep.getStepId(),
                executionStep.getProviderId(),
                executionStep.getInputType(),
                executionStep.getOutputType(),
                executionStep.getState().name()
        );
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
