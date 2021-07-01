package br.edu.ifsp.scl.pipegene.usecases.project;

import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.web.model.project.ProjectUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProjectCRUD {

    Project createNewProject(String name, String description, List<MultipartFile> files);

    Project findProjectById(UUID projectId);

    Project updateProjectById(UUID projectId, ProjectUpdateRequest request);

    List<Project> findAllProjects();

    void deleteProjectById(UUID projectId);
}
