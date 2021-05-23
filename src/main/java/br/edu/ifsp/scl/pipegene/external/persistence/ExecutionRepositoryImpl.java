package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStatus;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ExecutionStatusEntity;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ExecutionRepositoryImpl implements ExecutionRepository {

    @Override
    public Boolean projectExists(UUID projectId) {
        return FakeDatabase.PROJECTS.containsKey(projectId);
    }

    @Override
    public Boolean bathProviderInfoIsValid(List<Provider> providersBatch) {
        return providersBatch.stream()
                .anyMatch(p -> !FakeDatabase.PROVIDERS.containsKey(p.getId()));
    }

    @Override
    public Optional<ExecutionStatus> findExecutionStatusByProjectIdAndExecutionId(UUID projectId, UUID executionId) {
        if (FakeDatabase.EXECUTION_STATUS_MAP.containsKey(executionId)) {
            return Optional.of(FakeDatabase.EXECUTION_STATUS_MAP.get(executionId).toExecutionStatus());
        }
        return Optional.empty();
    }

    @Override
    public void saveExecutionStatus(ExecutionStatus executionStatus) {
        ExecutionStatusEntity entity = ExecutionStatusEntity.of(executionStatus);
        FakeDatabase.EXECUTION_STATUS_MAP.put(entity.getId(), entity);
    }
}
