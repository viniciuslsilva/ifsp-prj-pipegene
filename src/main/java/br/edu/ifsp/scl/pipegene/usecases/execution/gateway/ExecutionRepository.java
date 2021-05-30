package br.edu.ifsp.scl.pipegene.usecases.execution.gateway;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ExecutionRepository {

    Boolean bathProviderInfoIsValid(List<Provider> providers);

    Optional<Execution> findExecutionByProjectIdAndExecutionId(UUID projectId, UUID executionId);

    Optional<Execution> findExecutionByExecutionId(UUID executionId);

    void saveExecution(Execution execution);

    void updateExecution(Execution execution);

    Optional<Execution> findExecutionByExecutionIdAndCurrentExecutionStepId(UUID executionId, UUID stepId);
}
