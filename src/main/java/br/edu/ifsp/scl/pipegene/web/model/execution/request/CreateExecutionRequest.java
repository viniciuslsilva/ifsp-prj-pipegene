package br.edu.ifsp.scl.pipegene.web.model.execution.request;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateExecutionRequest {

    @NotNull
    private UUID pipelineId;

    @NotNull
    private UUID datasetId;

    private String description;

    public CreateExecutionRequest() {
    }

    public UUID getPipelineId() {
        return pipelineId;
    }

    public UUID getDatasetId() {
        return datasetId;
    }

    public String getDescription() {
        return description;
    }
}
