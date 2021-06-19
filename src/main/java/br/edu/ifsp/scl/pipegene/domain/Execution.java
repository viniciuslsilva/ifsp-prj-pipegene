package br.edu.ifsp.scl.pipegene.domain;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class Execution {
    private UUID id;
    private Pipeline pipeline;
    private Dataset dataset;
    private String description;
    private ExecutionStatusEnum status;
    private List<ExecutionStep> steps;

    private URI executionResult;
    private Integer currentStep = 0;

    public ExecutionStep getFirstStep() {
        if (currentStep > 0) {
            return null; // TODO add exception
        }
        status = ExecutionStatusEnum.IN_PROGRESS;
        return steps.get(0);
    }

    public UUID getProviderIdFromCurrentStep() {
        return steps.get(currentStep).getExecutionId();
    }

    public UUID getStepIdFromCurrentStep() {
        return steps.get(currentStep).getId();
    }

    public boolean hasNextStep() {
        return currentStep < steps.size() - 1;
    }

    public ExecutionStep getNextStep() {
        if (hasNextStep()) {
            ExecutionStep current = steps.get(currentStep);

            if (!current.getState().equals(ExecutionStepState.SUCCESS)) {
                return null; // TODO add exception
            }

            currentStep++;
            return steps.get(currentStep);
        }
        return null;
    }

    public void finishExecution(URI executionResult) {
        if (hasNextStep()) {
            throw new IllegalStateException();
        }
        status = ExecutionStatusEnum.DONE;
        this.executionResult = executionResult;
    }

    public void setCurrentExecutionStepState(ExecutionStepState state) {
        steps.get(currentStep).setState(state);
    }


    public Execution(UUID id, Pipeline pipeline, Dataset dataset, String description, List<ExecutionStep> steps) {
        this.id = id;
        this.pipeline = pipeline;
        this.dataset = dataset;
        this.description = description;
        this.steps = steps;
    }

    public static Execution createWithWaitingStatus(UUID id, Pipeline pipeline, Dataset dataset, String description) {
        List<ExecutionStep> executionSteps = pipeline.getSteps().stream()
                .map(p -> ExecutionStep.createFromPipelineStep(p, id))
                .collect(Collectors.toList());

        return new Execution(id, pipeline, dataset, description, ExecutionStatusEnum.WAITING, executionSteps);
    }

    private Execution(UUID id, Pipeline pipeline, Dataset dataset, String description, ExecutionStatusEnum status,
                      List<ExecutionStep> steps) {
        this.id = id;
        this.pipeline = pipeline;
        this.dataset = dataset;
        this.description = description;
        this.status = status;
        this.steps = steps;
    }


    public static Execution createWithoutSteps(UUID id, Pipeline pipeline, Dataset dataset, String description,
                                               ExecutionStatusEnum status, Integer currentStep, URI executionResult) {
        return new Execution(id, pipeline, dataset, description, status, currentStep, new ArrayList<>(), executionResult);
    }

    private Execution(UUID id, Pipeline pipeline, Dataset dataset, String description, ExecutionStatusEnum status,
                      Integer currentStep, List<ExecutionStep> steps, URI executionResult) {
        this.id = id;
        this.pipeline = pipeline;
        this.dataset = dataset;
        this.description = description;
        this.status = status;
        this.currentStep = currentStep;
        this.steps = steps;
        this.executionResult = executionResult;
    }

    public UUID getId() {
        return id;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }


    public String getDescription() {
        return description;
    }

    public ExecutionStatusEnum getStatus() {
        return status;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public List<ExecutionStep> getSteps() {
        return new ArrayList<>(steps);
    }

    public void setSteps(List<ExecutionStep> steps) {
        this.steps.addAll(steps);
    }

    public Dataset getDataset() {
        return dataset;
    }

    public URI getExecutionResult() {
        return executionResult;
    }
}
