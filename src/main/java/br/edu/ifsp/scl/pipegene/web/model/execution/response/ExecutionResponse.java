package br.edu.ifsp.scl.pipegene.web.model.execution.response;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.web.model.DatasetDTO;
import br.edu.ifsp.scl.pipegene.web.model.pipeline.response.PipelineResponse;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExecutionResponse {
    private UUID id;
    private PipelineResponse pipeline;
    private DatasetDTO dataset;
    private String description;
    private String status;

    private URI executionResult;
    private String errorMessage;

    private List<ExecutionStepResponse> steps;

    public static ExecutionResponse createJustId(UUID id) {
        return new ExecutionResponse(id);
    }

    public static ExecutionResponse createFromExecution(Execution e) {
        return new ExecutionResponse(
                e.getId(),
                PipelineResponse.createJustIdAndDescriptionFromPipeline(e.getPipeline()),
                DatasetDTO.createFromDataset(e.getDataset()),
                e.getDescription(),
                e.getStatus().name(),
                e.getExecutionResult(),
                e.getErrorMessage(),
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

    private ExecutionResponse(UUID id, PipelineResponse pipeline, DatasetDTO dataset, String description, String status,
                              URI executionResult, String errorMessage, List<ExecutionStepResponse> steps) {
        this.id = id;
        this.pipeline = pipeline;
        this.dataset = dataset;
        this.description = description;
        this.status = status;
        this.executionResult = executionResult;
        this.errorMessage = errorMessage;
        this.steps = steps;
    }

    public UUID getId() {
        return id;
    }

    public PipelineResponse getPipeline() {
        return pipeline;
    }

    public DatasetDTO getDataset() {
        return dataset;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public URI getExecutionResult() {
        return executionResult;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<ExecutionStepResponse> getSteps() {
        return steps;
    }
}
