package br.edu.ifsp.scl.pipegene.usecases.project;

import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectRepository;
import br.edu.ifsp.scl.pipegene.web.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ObjectStorageService objectStorageService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ObjectStorageService objectStorageService) {
        this.projectRepository = projectRepository;
        this.objectStorageService = objectStorageService;
    }

    @Override
    public Project createNewProject(String name, String description, List<MultipartFile> files) {
        List<String> datasets = files.stream().map(objectStorageService::putObject).collect(Collectors.toList());

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


}
