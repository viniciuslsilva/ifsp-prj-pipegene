package br.edu.ifsp.scl.pipegene.web.model.pipeline.request;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CreatePipelineRequest {

    @NotNull
    private List<PipelineStepRequest> steps;

    @NotNull
    private String description;

    public CreatePipelineRequest() { }

    public boolean executionStepsIsEmpty() {
        return steps.isEmpty();
    }

    public List<PipelineStepRequest> getSteps() {
        return steps;
    }

    public String getDescription() {
        return description;
    }
}
