package br.edu.ifsp.scl.pipegene.usecases.project;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectRepository;
import br.edu.ifsp.scl.pipegene.web.exception.ResourceNotFoundException;
import br.edu.ifsp.scl.pipegene.web.model.project.ProjectUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectCRUDImpl implements ProjectCRUD {

    private final ProjectRepository projectRepository;
    private final ObjectStorageService objectStorageService;

    public ProjectCRUDImpl(ProjectRepository projectRepository, ObjectStorageService objectStorageService) {
        this.projectRepository = projectRepository;
        this.objectStorageService = objectStorageService;
    }

    @Override
    public Project createNewProject(String name, String description, List<MultipartFile> files) {
        List<Dataset> datasets = files.stream().map(objectStorageService::putObject).collect(Collectors.toList());

        return projectRepository.saveNewProject(name, description, datasets);
    }

    @Override
    public Project findProjectById(UUID projectId) {
        Optional<Project> optional = projectRepository.findProjectById(projectId);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        return optional.get();
    }

    @Override
    public Project updateProjectById(UUID projectId, ProjectUpdateRequest request) {
        Optional<Project> optional = projectRepository.findProjectById(projectId);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        return projectRepository.updateProject(
                optional.get().getUpdatedInstance(request)
        );
    }

    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAllProjects();
    }
}
