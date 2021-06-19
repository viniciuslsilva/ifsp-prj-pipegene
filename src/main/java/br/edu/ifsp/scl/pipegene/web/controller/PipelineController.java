package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.domain.Pipeline;
import br.edu.ifsp.scl.pipegene.usecases.pipeline.PipelineCRUD;
import br.edu.ifsp.scl.pipegene.web.model.pipeline.request.CreatePipelineRequest;
import br.edu.ifsp.scl.pipegene.web.model.pipeline.response.PipelineResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("api/v1/projects/{projectId}/pipelines")
@RestController
public class PipelineController {

    private final PipelineCRUD pipelineCRUD;

    public PipelineController(PipelineCRUD pipelineCRUD) {
        this.pipelineCRUD = pipelineCRUD;
    }

    @PostMapping
    public ResponseEntity<PipelineResponse> addNewPipeline(
            @PathVariable UUID projectId,
            @RequestBody @Valid CreatePipelineRequest createPipelineRequest) {
        Pipeline pipeline = pipelineCRUD.addNewPipeline(projectId, createPipelineRequest);

        return ResponseEntity.ok(PipelineResponse.createJustId(pipeline.getId()));
    }

    @GetMapping
    public ResponseEntity<List<PipelineResponse>> listAll(@PathVariable UUID projectId) {
        List<Pipeline> pipelines = pipelineCRUD.findAllPipeline(projectId);

        return ResponseEntity.ok(
                pipelines.stream()
                        .map(PipelineResponse::createFromPipeline)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{pipelineId}")
    public ResponseEntity<PipelineResponse> findById(@PathVariable UUID projectId, @PathVariable UUID pipelineId) {
        Pipeline pipeline = pipelineCRUD.findByProjectIdAndPipelineId(projectId, pipelineId);

        return ResponseEntity.ok(PipelineResponse.createFromPipeline(pipeline));
    }
}
