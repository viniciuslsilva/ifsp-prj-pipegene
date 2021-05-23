package br.edu.ifsp.scl.pipegene.usecases.project;

import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ObjectStorageService objectStorageService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ObjectStorageService objectStorageService) {
        this.projectRepository = projectRepository;
        this.objectStorageService = objectStorageService;
    }


    @Override
    public Project createNewProject(String name, MultipartFile file) {
        String datasetUrl = objectStorageService.putObject(file);
        return projectRepository.saveNewProject(name, datasetUrl);
    }

}
