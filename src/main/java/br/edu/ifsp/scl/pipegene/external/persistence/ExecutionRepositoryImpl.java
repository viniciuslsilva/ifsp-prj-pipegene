package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ExecutionEntity;
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
    public Optional<Execution> findExecutionByProjectIdAndExecutionId(UUID projectId, UUID executionId) {
        if (fakeDatabase.EXECUTION_STATUS_MAP.containsKey(executionId)) {
            Project project = projectRepository.findProjectById(projectId).orElseThrow();
            return Optional.of(fakeDatabase.EXECUTION_STATUS_MAP.get(executionId).convertToExecution(project));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Execution> findExecutionByExecutionId(UUID executionId) {
        if (fakeDatabase.EXECUTION_STATUS_MAP.containsKey(executionId)) {
            return Optional.of(fakeDatabase.EXECUTION_STATUS_MAP.get(executionId))
                    .map(execution -> execution.convertToExecution(
                            projectRepository.findProjectById(execution.getProjectId())
                                    .orElseThrow()
                            )
                    );
        }
        return Optional.empty();
    }

    @Override
    public Execution saveExecution(Execution execution) {

        ExecutionEntity entity = ExecutionEntity.createNewEntity(execution);
        fakeDatabase.EXECUTION_STATUS_MAP.put(entity.getId(), entity);
        Project project =fakeDatabase.PROJECTS.get(entity.getProjectId()).convertToProject();
        return entity.convertToExecution(project);
    }

    @Override
    public void updateExecution(Execution execution) {
        ExecutionEntity entity = ExecutionEntity.createNewEntity(execution);
        fakeDatabase.EXECUTION_STATUS_MAP.replace(entity.getId(), entity);
    }

    @Override
    public Optional<Execution> findExecutionByExecutionIdAndCurrentExecutionStepId(UUID executionId, UUID stepId) {
        if (fakeDatabase.EXECUTION_STATUS_MAP.containsKey(executionId)) {
            ExecutionEntity entity = fakeDatabase.EXECUTION_STATUS_MAP.get(executionId);
            Project project = projectRepository.findProjectById(entity.getProjectId()).orElseThrow();
            Execution execution = entity.convertToExecution(project);

            if (execution.getStepIdFromCurrentExecutionStep().equals(stepId)) {
                return Optional.of(execution);
            }
        }
        return Optional.empty();
    }
}
