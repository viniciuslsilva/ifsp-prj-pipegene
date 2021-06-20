package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.domain.Pipeline;
import br.edu.ifsp.scl.pipegene.usecases.pipeline.PipelineCRUD;
import br.edu.ifsp.scl.pipegene.web.model.pipeline.response.PipelineResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users/{userId}/pipelines")
public class UserPipelineController {

    private final PipelineCRUD pipelineCRUD;

    public UserPipelineController(PipelineCRUD pipelineCRUD) {
        this.pipelineCRUD = pipelineCRUD;
    }

    @GetMapping
    public ResponseEntity<List<PipelineResponse>> listAllExecutions(@PathVariable UUID userId) {
        List<Pipeline> pipelines = pipelineCRUD.listAllPipelinesByUserId(userId);

        return ResponseEntity.ok(pipelines.stream()
                .map(PipelineResponse::createFromPipeline)
                .collect(Collectors.toList())
        );
    }

}
