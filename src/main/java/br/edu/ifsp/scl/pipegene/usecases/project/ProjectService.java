package br.edu.ifsp.scl.pipegene.usecases.project;

import br.edu.ifsp.scl.pipegene.domain.Project;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    Project createNewProject(String name, String description, List<MultipartFile> files);

    Project findProjectById(UUID projectId);

    List<Project> findAllProjects();
}
