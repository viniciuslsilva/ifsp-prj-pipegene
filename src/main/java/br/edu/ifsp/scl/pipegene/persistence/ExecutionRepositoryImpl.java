package br.edu.ifsp.scl.pipegene.persistence;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStatus;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionRepository;
import br.edu.ifsp.scl.pipegene.persistence.entities.ProjectEntity;
import br.edu.ifsp.scl.pipegene.persistence.entities.ExecutionStatusEntity;
import br.edu.ifsp.scl.pipegene.persistence.entities.ProviderEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ExecutionRepositoryImpl implements ExecutionRepository {

    private static Map<UUID, ExecutionStatusEntity> executionStatusMap = new HashMap<>();
    private static List<ProviderEntity> providers = loadMockProviders();
    private static List<ProjectEntity> projects = loadMockProjects();

    @Override
    public Boolean projectExists(UUID projectId) {
        return projects.stream().map(ProjectEntity::getId)
                .anyMatch(id -> id.equals(projectId));
    }

    public Optional<ProjectEntity> findProjectById(UUID id) {
        return Optional.of(
                ProjectEntity.of(
                        UUID.randomUUID(),
                        "updates/projectid/fake.maf",
                        "Fake Project")
        );
    }

    @Override
    public Boolean bathProviderInfoIsValid(List<Provider> providersBatch) {
        return providersBatch.stream().filter(p -> providers.stream()
                .map(ProviderEntity::toProvider)
                .map(Provider::getId)
                .anyMatch(id -> id.equals(p.getId())))
                .count() == providersBatch.size();
    }

    @Override
    public Optional<ExecutionStatus> findExecutionStatusByProjectIdAndExecutionId(UUID projectId, UUID executionId) {
        if (executionStatusMap.containsKey(executionId)) {
            return Optional.of(executionStatusMap.get(executionId).toExecutionStatus());
        }
        return Optional.empty();
    }

    @Override
    public void saveExecutionStatus(ExecutionStatus executionStatus) {
        ExecutionStatusEntity entity = ExecutionStatusEntity.of(executionStatus);
        executionStatusMap.put(entity.getId(), entity);
    }


    private static List<ProviderEntity> loadMockProviders() {
        return Arrays.asList(
                ProviderEntity.of(
                        UUID.fromString("baca1f38-5501-476f-a7f5-fe5958a55772"),
                        Collections.singletonList("maf"),
                        Collections.singletonList("maf")
                ),
                ProviderEntity.of(
                        UUID.fromString("0fd493f8-fb58-455a-8cb9-d3561d111e70"),
                        Collections.singletonList("taf"),
                        Collections.singletonList("taf")
                )
        );
    }

    private static List<ProjectEntity> loadMockProjects() {
        return Arrays.asList(
                ProjectEntity.of(
                        UUID.fromString("53bb719a-e982-4785-bb53-40dc71d6dd9a"),
                        "updates/projectid/fake.maf",
                        "Fake Project"
                ),
                ProjectEntity.of(
                        UUID.fromString("e1d33cc3-f04d-45c8-8998-20cd0d4af878"),
                        "updates/projectid/fake.maf",
                        "Fake Project 2"
                )

        );
    }
}
