package br.edu.ifsp.scl.pipegene.usecases.execution.gateway;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStatus;
import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ExecutionRepository {

    Boolean projectExists(UUID id);

    Boolean bathProviderInfoIsValid(List<Provider> providers);

    Optional<ExecutionStatus> findExecutionStatusByProjectIdAndExecutionId(UUID projectId, UUID executionId);

    void saveExecutionStatus(ExecutionStatus executionStatus);

}
