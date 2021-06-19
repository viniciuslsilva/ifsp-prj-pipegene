package br.edu.ifsp.scl.pipegene.usecases.project.gateway;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectDAO {

    Project saveNewProject(String name, String description, List<Dataset> datasets);

    Project updateProject(Project project);

    Optional<Project> findProjectById(UUID id);

    List<Project> findAllProjects();

    Boolean projectExists(UUID id);
}
