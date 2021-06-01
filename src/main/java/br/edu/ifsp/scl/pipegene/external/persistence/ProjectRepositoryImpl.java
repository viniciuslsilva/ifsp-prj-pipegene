package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ProjectEntity;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final FakeDatabase fakeDatabase;

    public ProjectRepositoryImpl(FakeDatabase fakeDatabase) {
        this.fakeDatabase = fakeDatabase;
    }

    @Override
    public Project saveNewProject(String name, String description, List<Dataset> datasets) {
        ProjectEntity entity = ProjectEntity.createNewEntity(datasets, name, description);
        fakeDatabase.PROJECTS.put(entity.getId(), entity);

        return entity.convertToProject();
    }

    @Override
    public Optional<Project> findProjectById(UUID id) {
        if (fakeDatabase.PROJECTS.containsKey(id)) {
            return Optional.of(fakeDatabase.PROJECTS.get(id).convertToProject());
        }

        return Optional.empty();
    }

    @Override
    public Boolean projectExists(UUID projectId) {
        return fakeDatabase.PROJECTS.containsKey(projectId);
    }

    @Override
    public List<Project> findAllProjects() {
        return fakeDatabase.PROJECTS.values().stream()
                .map(ProjectEntity::convertToProject)
                .collect(Collectors.toList());
    }

    @Override
    public List<Execution> findAllExecutionsByProjectId(UUID projectId) {
        Project project = fakeDatabase.PROJECTS.get(projectId).convertToProject();
        return fakeDatabase.EXECUTION_STATUS_MAP.values().stream()
                .filter(executionEntity -> executionEntity.getProjectId().equals(projectId))
                .map(executionEntity -> executionEntity.convertToExecution(project))
                .collect(Collectors.toList());
    }
}
