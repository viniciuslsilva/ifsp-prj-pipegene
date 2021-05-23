package br.edu.ifsp.scl.pipegene.usecases.project.gateway;

import br.edu.ifsp.scl.pipegene.domain.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository {

    Project saveNewProject(String name, String datasetUrl);
}
