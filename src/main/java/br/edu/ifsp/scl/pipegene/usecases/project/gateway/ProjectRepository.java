package br.edu.ifsp.scl.pipegene.usecases.project.gateway;

import br.edu.ifsp.scl.pipegene.domain.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository {

    Project saveNewProject(String name, String description, List<String> datasets);

    Optional<Project> findProjectById(UUID id);

    Boolean projectExists(UUID id);
}
