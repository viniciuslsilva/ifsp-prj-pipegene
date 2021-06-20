package br.edu.ifsp.scl.pipegene.usecases.pipeline.gateway;

import br.edu.ifsp.scl.pipegene.domain.Pipeline;
import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PipelineDAO {

    Boolean providerListIsValid(List<Provider> providers);

    Pipeline savePipeline(Pipeline pipeline);

    List<Pipeline> findAll(UUID projectId);

    List<Pipeline> listAllByOwnerId(UUID ownerId);

    Collection<Pipeline> findPipelinesByProjectId(UUID projectId);

    Optional<Pipeline> findPipelineById(UUID pipelineId);
}
