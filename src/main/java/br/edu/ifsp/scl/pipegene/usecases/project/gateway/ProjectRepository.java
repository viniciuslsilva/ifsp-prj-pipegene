package br.edu.ifsp.scl.pipegene.usecases.project.gateway;

import br.edu.ifsp.scl.pipegene.domain.Project;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository {

    Project saveNewProject(String name, String datasetUrl);

    Optional<Project> findProjectById(UUID id);
}
