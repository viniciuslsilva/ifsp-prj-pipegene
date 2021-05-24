package br.edu.ifsp.scl.pipegene.domain;

import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequestFlowDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExecutionStatus {
    private UUID id;
    private UUID projectId;
    private ExecutionStatusEnum status;

    private Integer currentStep = 0;
    private List<WrapperExecutionProgress> steps = new ArrayList<>();

    private ExecutionStatus(UUID id, UUID projectId, ExecutionStatusEnum status) {
        this.id = id;
        this.projectId = projectId;
        this.status = status;
    }

    public void addExecutionRequestFlowDetails(List<ExecutionRequestFlowDetails> executionRequestFlowDetails) {
        steps = executionRequestFlowDetails.stream()
                .map(e -> new WrapperExecutionProgress(ExecutionStepState.NOT_EXECUTED, e))
                .collect(Collectors.toList());
    }

    public ExecutionRequestFlowDetails firstStep() {
        if (currentStep != 0) {
            return null; // TODO add exception
        }
        status = ExecutionStatusEnum.IN_PROGRESS;
        return steps.get(currentStep).executionRequestFlowDetails;
    }

    public ExecutionRequestFlowDetails nextStep() {
        if (currentStep < steps.size()) {
            WrapperExecutionProgress current = steps.get(currentStep);

            if (!current.state.equals(ExecutionStepState.SUCCESS)) {
                return null; // TODO add exception
            }

            currentStep++;
            WrapperExecutionProgress next = steps.get(currentStep);
            return next.executionRequestFlowDetails;
        }
        return null;
    }

    public static ExecutionStatus of(UUID id, UUID projectId, ExecutionStatusEnum status) {
        return new ExecutionStatus(id, projectId, status);
    }

    public UUID getId() {
        return id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public ExecutionStatusEnum getStatus() {
        return status;
    }

     class WrapperExecutionProgress {
        private ExecutionStepState state;
        private ExecutionRequestFlowDetails executionRequestFlowDetails;

        public WrapperExecutionProgress(ExecutionStepState state, ExecutionRequestFlowDetails executionRequestFlowDetails) {
            this.state = state;
            this.executionRequestFlowDetails = executionRequestFlowDetails;
        }

        private void setState(ExecutionStepState state) {
            this.state = state;
        }
    }
}
