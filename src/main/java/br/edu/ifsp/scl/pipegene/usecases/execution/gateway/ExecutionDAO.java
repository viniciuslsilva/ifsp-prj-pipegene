package br.edu.ifsp.scl.pipegene.usecases.execution.gateway;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ExecutionDAO {

    Optional<Execution> findExecutionByProjectIdAndExecutionId(UUID projectId, UUID executionId);

    Boolean existsExecutionIdAndStepIdForProvider(UUID executionId, UUID stepId, UUID providerId);

    Optional<Execution> findExecutionByExecutionId(UUID executionId);

    Execution saveExecution(Execution execution);

    void updateExecution(Execution execution);

    List<Execution> findAllExecutionsByProjectId(UUID projectId);
}
