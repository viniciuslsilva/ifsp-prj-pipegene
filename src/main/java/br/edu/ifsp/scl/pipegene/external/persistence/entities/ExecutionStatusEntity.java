package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStatus;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStatusEnum;
import br.edu.ifsp.scl.pipegene.domain.Project;

import java.util.List;
import java.util.UUID;

public class ExecutionStatusEntity {
    private UUID id;
    private UUID projectId;
    private String status;

    private Integer currentStep;
    private List<?> steps;

    // TODO("Add attributes created_at and limit_date")

    private ExecutionStatusEntity(UUID id, UUID projectId, String status, Integer currentStep, List<?> steps) {
        this.id = id;
        this.projectId = projectId;
        this.status = status;
        this.currentStep = currentStep;
        this.steps = steps;
    }

    public static ExecutionStatusEntity of(ExecutionStatus executionStatus) {
        return new ExecutionStatusEntity(
                executionStatus.getId(),
                executionStatus.getProject().getId(),
                executionStatus.getStatus().name(),
                executionStatus.getCurrentStep(),
                executionStatus.getSteps()
        );
    }

    public ExecutionStatus toExecutionStatus(Project project) {
        return ExecutionStatus.of(id, project, ExecutionStatusEnum.valueOf(status), currentStep, steps);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
