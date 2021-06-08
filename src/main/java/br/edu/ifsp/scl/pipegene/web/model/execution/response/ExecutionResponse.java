package br.edu.ifsp.scl.pipegene.web.model.execution.response;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.web.model.DatasetDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecutionResponse {
    private UUID id;
    private Project project;
    private DatasetDTO dataset;
    private String status;

    private URI executionResult;

    private List<ExecutionStepResponse> steps;

    public static ExecutionResponse createJustId(UUID id) {
        return new ExecutionResponse(id);
    }

    public static ExecutionResponse createFromExecution(Execution e) {
        return new ExecutionResponse(
                e.getId(),
                e.getProject(),
                DatasetDTO.createFromDataset(e.getDataset()),
                e.getStatus().name(),
                e.getExecutionResult(),
                e.getSteps().stream()
                        .map(ExecutionStepResponse::createFromExecutionStep)
                        .collect(Collectors.toList())
        );
    }

    private ExecutionResponse(UUID id) {
        this.id = id;
    }

    private ExecutionResponse() {
    }

    private ExecutionResponse(UUID id, Project project, DatasetDTO dataset, String status,
                              URI executionResult, List<ExecutionStepResponse> steps) {
        this.id = id;
        this.project = project;
        this.dataset = dataset;
        this.status = status;
        this.executionResult = executionResult;
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

    public DatasetDTO getDataset() {
        return dataset;
    }

    public void setDataset(DatasetDTO dataset) {
        this.dataset = dataset;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public URI getExecutionResult() {
        return executionResult;
    }

    public void setExecutionResult(URI executionResult) {
        this.executionResult = executionResult;
    }

    public List<ExecutionStepResponse> getSteps() {
        return steps;
    }

    public void setSteps(List<ExecutionStepResponse> steps) {
        this.steps = steps;
    }
}
