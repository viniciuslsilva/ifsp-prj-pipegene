package br.edu.ifsp.scl.pipegene.domain;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Execution {
    private UUID id;
    private Project project;
    private String dataset;
    private ExecutionStatusEnum status;

    private URI executionResult;

    private Integer currentStep = 0;
    private List<ExecutionStep> steps = new ArrayList<>();

    public void setExecutionSteps(List<ExecutionStep> steps) {
        this.steps = steps;
    }

    public ExecutionStep getFirstExecutionStep() {
        if (currentStep > 0) {
            return null; // TODO add exception
        }
        status = ExecutionStatusEnum.IN_PROGRESS;
        return steps.get(0);
    }

    public UUID getProviderIdFromCurrentExecutionStep() {
        return steps.get(currentStep).getProviderId();
    }

    public UUID getStepIdFromCurrentExecutionStep() {
        return steps.get(currentStep).getStepId();
    }

    public boolean hasNextExecution() {
        return currentStep < steps.size() - 1;
    }

    public ExecutionStep getNextExecutionStep() {
        if (hasNextExecution()) {
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
        if (hasNextExecution()) {
            throw new IllegalStateException();
        }
        status = ExecutionStatusEnum.DONE;
        this.executionResult = executionResult;
    }

    public void setCurrentExecutionStepState(ExecutionStepState state) {
        steps.get(currentStep).setState(state);
    }

    private Execution(UUID id, Project project, String dataset, ExecutionStatusEnum status) {
        this.id = id;
        this.project = project;
        this.dataset = dataset;
        this.status = status;
    }

    private Execution(UUID id, Project project, String dataset, ExecutionStatusEnum status, Integer currentStep, List<ExecutionStep> steps) {
        this.id = id;
        this.project = project;
        this.dataset = dataset;
        this.status = status;
        this.currentStep = currentStep;
        this.steps = steps;
    }


    public static Execution of(UUID id, Project project, String dataset, ExecutionStatusEnum status) {
        return new Execution(id, project, dataset, status);
    }

    public static Execution of(UUID id, Project project, String dataset, ExecutionStatusEnum status, Integer currentStep, List<ExecutionStep> steps) {
        return new Execution(id, project, dataset, status, currentStep, steps);
    }

    public UUID getId() {
        return id;
    }

    public Project getProject() {
        return project;
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

    public String getDataset() {
        return dataset;
    }

    public URI getExecutionResult() {
        return executionResult;
    }
}
