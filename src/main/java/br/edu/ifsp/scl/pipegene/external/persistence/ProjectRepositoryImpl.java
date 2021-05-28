package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ProjectEntity;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final FakeDatabase fakeDatabase;

    public ProjectRepositoryImpl(FakeDatabase fakeDatabase) {
        this.fakeDatabase = fakeDatabase;
    }

    @Override
    public Project saveNewProject(String name, String description, List<String> datasets) {
        ProjectEntity entity = ProjectEntity.of(UUID.randomUUID(), datasets, name, description);
        fakeDatabase.PROJECTS.put(entity.getId(), entity);

        return entity.toProject();
    }

    @Override
    public Optional<Project> findProjectById(UUID id) {
        if (fakeDatabase.PROJECTS.containsKey(id)) {
            return Optional.of(fakeDatabase.PROJECTS.get(id).toProject());
        }

        return Optional.empty();
    }

    @Override
    public Boolean projectExists(UUID projectId) {
        return fakeDatabase.PROJECTS.containsKey(projectId);
    }


}
