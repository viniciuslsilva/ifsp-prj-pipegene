package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStatusEnum;
import br.edu.ifsp.scl.pipegene.domain.Project;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExecutionEntity {
    private UUID id;
    private UUID projectId;
    private DatasetEntity dataset;
    private String status;
    private Integer currentStep;
    private List<ExecutionStepEntity> steps;
    private URI executionResult;

    // TODO("Add attributes created_at and limit_date")


    public static ExecutionEntity createNewEntity(Execution execution) {
        return new ExecutionEntity(
                execution.getId(),
                execution.getProject().getId(),
                DatasetEntity.createFromDataset(execution.getDataset()),
                execution.getStatus().name(),
                execution.getCurrentStep(),
                execution.getSteps().stream().map(ExecutionStepEntity::of).collect(Collectors.toList()),
                execution.getExecutionResult()
        );
    }

    public Execution convertToExecution(Project project) {
        return Execution.createWithAllValues(id, project, dataset.convertToDataset(), ExecutionStatusEnum.valueOf(status),
                currentStep, steps.stream()
                        .map(ExecutionStepEntity::toExecutionStep)
                        .collect(Collectors.toList()),
                executionResult
        );
    }

    public ExecutionEntity() { }

    private ExecutionEntity(UUID id, UUID projectId, DatasetEntity dataset, String status, Integer currentStep, List<ExecutionStepEntity> steps, URI executionResult) {
        this.id = id;
        this.projectId = projectId;
        this.dataset = dataset;
        this.status = status;
        this.currentStep = currentStep;
        this.steps = new ArrayList<>(steps);
        this.executionResult = executionResult;
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

    public DatasetEntity getDataset() {
        return dataset;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public List<ExecutionStepEntity> getSteps() {
        return new ArrayList<>(steps);
    }

    public URI getExecutionResult() {
        return executionResult;
    }
}
