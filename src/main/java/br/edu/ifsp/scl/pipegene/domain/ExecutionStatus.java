package br.edu.ifsp.scl.pipegene.domain;

import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequestFlowDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExecutionStatus {
    private UUID id;
    private Project project;
    private ExecutionStatusEnum status;

    private Integer currentStep = 0;
    private List<WrapperExecutionProgress> steps = new ArrayList<>();

    private ExecutionStatus(UUID id, Project project, ExecutionStatusEnum status) {
        this.id = id;
        this.project = project;
        this.status = status;
    }

    private ExecutionStatus(UUID id, Project project, ExecutionStatusEnum status, Integer currentStep, List<WrapperExecutionProgress> steps) {
        this.id = id;
        this.project = project;
        this.status = status;
        this.currentStep = currentStep;
        this.steps = steps;
    }

    public void setExecutionFlow(List<ExecutionRequestFlowDetails> executionRequestFlowDetails) {
        steps = executionRequestFlowDetails.stream()
                .map(e -> new WrapperExecutionProgress(ExecutionStepState.NOT_EXECUTED, e))
                .collect(Collectors.toList());
    }

    public ExecutionRequestFlowDetails getFirstExecutionDetails() {
        if (currentStep != 0) {
            return null; // TODO add exception
        }
        status = ExecutionStatusEnum.IN_PROGRESS;
        return steps.get(currentStep).executionRequestFlowDetails;
    }

    public UUID getProviderFromCurrentExecution() {
        return steps.get(currentStep).executionRequestFlowDetails.getProviderId();
    }

    public ExecutionRequestFlowDetails getNextExecutionDetails() {
        if (currentStep < steps.size()) {
            WrapperExecutionProgress current = steps.get(currentStep);

            if (!current.state.equals(ExecutionStepState.SUCCESS)) {
                return null; // TODO add exception
            }

            currentStep++;
            WrapperExecutionProgress next = steps.get(currentStep);
            return next.executionRequestFlowDetails;
        } else {
            // Logic when all steps are finished
        }
        return null;
    }

    public static ExecutionStatus of(UUID id, Project project, ExecutionStatusEnum status) {
        return new ExecutionStatus(id, project, status);
    }

    public static ExecutionStatus of(UUID id, Project project, ExecutionStatusEnum status, Integer currentStep, List<?> steps) {
        return new ExecutionStatus(id, project, status, currentStep, (List<WrapperExecutionProgress>) steps);
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

    public List<WrapperExecutionProgress> getSteps() {
        return new ArrayList<>(steps);
    }

    public void setCurrentExecutionState(ExecutionStepState state) {
        steps.get(currentStep).state = state;
    }

    class WrapperExecutionProgress {
        private ExecutionStepState state;
        private ExecutionRequestFlowDetails executionRequestFlowDetails;

        public WrapperExecutionProgress(ExecutionStepState state, ExecutionRequestFlowDetails executionRequestFlowDetails) {
            this.state = state;
            this.executionRequestFlowDetails = executionRequestFlowDetails;
        }

        @Override
        public String toString() {
            return "WrapperExecutionProgress{" +
                    "state=" + state +
                    ", executionRequestFlowDetails=" + executionRequestFlowDetails +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ExecutionStatus{" +
                "id=" + id +
                ", project=" + project +
                ", status=" + status +
                ", currentStep=" + currentStep +
                ", steps=" + steps +
                '}';
    }
}
