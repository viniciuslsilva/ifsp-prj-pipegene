package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ProjectEntity;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    @Override
    public Project saveNewProject(String name, String datasetUrl) {
        ProjectEntity entity = ProjectEntity.of(UUID.randomUUID(), datasetUrl, name);
        FakeDatabase.PROJECTS.put(entity.getId(), entity);

        return entity.toProject();
    }
}
