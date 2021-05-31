package br.edu.ifsp.scl.pipegene.web.model.execution.response;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStatusEnum;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStep;
import br.edu.ifsp.scl.pipegene.domain.Project;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExecutionResponse {

    private UUID id;
    private Project project;
    private String dataset;
    private ExecutionStatusEnum status;

    private URI executionResult;

    private Integer currentStep;
    private List<ExecutionStep> steps;

    public ExecutionResponse() {
    }

    public ExecutionResponse(UUID id, Project project, String dataset, ExecutionStatusEnum status,
                             URI executionResult, Integer currentStep, List<ExecutionStep> steps) {
        this.id = id;
        this.project = project;
        this.dataset = dataset;
        this.status = status;
        this.executionResult = executionResult;
        this.currentStep = currentStep;
        this.steps = steps;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public ExecutionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatusEnum status) {
        this.status = status;
    }

    public URI getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(URI executionResult) {
        this.executionResult = executionResult;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public List<ExecutionStep> getSteps() {
        return steps;
    }

    public void setSteps(List<ExecutionStep> steps) {
        this.steps = steps;
    }
}
