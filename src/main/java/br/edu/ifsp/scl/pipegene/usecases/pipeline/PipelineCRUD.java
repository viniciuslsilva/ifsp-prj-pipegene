package br.edu.ifsp.scl.pipegene.usecases.pipeline;

import br.edu.ifsp.scl.pipegene.domain.Pipeline;
import br.edu.ifsp.scl.pipegene.web.model.pipeline.request.CreatePipelineRequest;

import java.util.List;
import java.util.UUID;

public interface PipelineCRUD {

    Pipeline addNewPipeline(UUID projectId, CreatePipelineRequest request);

    List<Pipeline> findAllPipeline(UUID projectId);

    Pipeline findByProjectIdAndPipelineId(UUID projectId, UUID pipelineId);
}
