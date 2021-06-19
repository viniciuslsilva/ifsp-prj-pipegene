package br.edu.ifsp.scl.pipegene.web.model.pipeline.response;

import br.edu.ifsp.scl.pipegene.domain.Pipeline;
import br.edu.ifsp.scl.pipegene.domain.Project;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PipelineResponse {

    private UUID id;
    private Project project;
    private String description;
    private List<PipelineStepResponse> steps;

    public static PipelineResponse createJustId(UUID id) {
        return new PipelineResponse(id);
    }

    public PipelineResponse(UUID id) {
        this.id = id;
    }

    public PipelineResponse(UUID id, Project project, String description, List<PipelineStepResponse> steps) {
        this.id = id;
        this.project = project;
        this.description = description;
        this.steps = steps;
    }

    public PipelineResponse() {
    }

    public UUID getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getDescription() {
        return description;
    }

    public List<PipelineStepResponse> getSteps() {
        return steps;
    }

    public static PipelineResponse createFromPipeline(Pipeline p) {
        return new PipelineResponse(
                p.getId(),
                p.getProject(),
                p.getDescription(),
                p.getSteps().stream().map(PipelineStepResponse::createFromPipelineStep).collect(Collectors.toList())
        );
    }

    public static PipelineResponse createJustIdAndDescriptionFromPipeline(Pipeline p) {
        return new PipelineResponse(p.getId(), p.getDescription());
    }

    private PipelineResponse(UUID id, String description) {
        this.id = id;
        this.description = description;
    }

    public static PipelineResponse createFromPipelineWithoutProject(Pipeline p) {
        return new PipelineResponse(
                p.getId(),
                null,
                p.getDescription(),
                p.getSteps().stream().map(PipelineStepResponse::createFromPipelineStep).collect(Collectors.toList())
        );
    }
}
