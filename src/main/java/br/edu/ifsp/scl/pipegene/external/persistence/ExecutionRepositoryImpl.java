package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStatus;
import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ExecutionStatusEntity;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionRepository;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ExecutionRepositoryImpl implements ExecutionRepository {

    private final FakeDatabase fakeDatabase;
    private final ProjectRepository projectRepository;

    public ExecutionRepositoryImpl(FakeDatabase fakeDatabase, ProjectRepository projectRepository) {
        this.fakeDatabase = fakeDatabase;
        this.projectRepository = projectRepository;
    }

    @Override
    public Boolean bathProviderInfoIsValid(List<Provider> providersBatch) {
        return providersBatch.stream()
                .filter(p -> fakeDatabase.PROVIDERS.containsKey(p.getId()))
                .count() == providersBatch.size();
    }

    @Override
    public Optional<ExecutionStatus> findExecutionStatusByProjectIdAndExecutionId(UUID projectId, UUID executionId) {
        if (fakeDatabase.EXECUTION_STATUS_MAP.containsKey(executionId)) {
            Project project = projectRepository.findProjectById(projectId).orElseThrow();
            return Optional.of(fakeDatabase.EXECUTION_STATUS_MAP.get(executionId).toExecutionStatus(project));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ExecutionStatus> findExecutionStatusByExecutionId(UUID executionId) {
        if (fakeDatabase.EXECUTION_STATUS_MAP.containsKey(executionId)) {
            return Optional.of(fakeDatabase.EXECUTION_STATUS_MAP.get(executionId))
                    .map(execution -> execution.toExecutionStatus(
                            projectRepository.findProjectById(execution.getProjectId())
                                    .orElseThrow()
                            )
                    );
        }
        return Optional.empty();
    }

    @Override
    public void saveExecutionStatus(ExecutionStatus executionStatus) {
        ExecutionStatusEntity entity = ExecutionStatusEntity.of(executionStatus);
        fakeDatabase.EXECUTION_STATUS_MAP.put(entity.getId(), entity);
    }

    @Override
    public void updateExecutionStatus(ExecutionStatus executionStatus) {
        ExecutionStatusEntity entity = ExecutionStatusEntity.of(executionStatus);
        fakeDatabase.EXECUTION_STATUS_MAP.replace(entity.getId(), entity);
    }


}
