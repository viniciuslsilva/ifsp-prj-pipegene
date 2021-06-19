package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.PipelineStep;

import java.util.Map;
import java.util.UUID;

public class ExecutionStepEntity {

    private UUID stepId;
    private UUID providerId;
    private String inputType;
    private String outputType;
    private String state;
    private Map<String, Object> executionStepParams;


    private ExecutionStepEntity(UUID stepId, UUID providerId, String inputType, String outputType, String state,
                                Map<String, Object> executionStepParams) {
        this.stepId = stepId;
        this.providerId = providerId;
        this.inputType = inputType;
        this.outputType = outputType;
        this.state = state;
        this.executionStepParams = executionStepParams;
    }

    public static ExecutionStepEntity of(PipelineStep pipelineStep) {
        return new ExecutionStepEntity(
                pipelineStep.getStepId(),
                null,
                pipelineStep.getInputType(),
                pipelineStep.getOutputType(),
                null,
                pipelineStep.getParams()
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
