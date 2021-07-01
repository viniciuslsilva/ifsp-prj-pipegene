package br.edu.ifsp.scl.pipegene.usecases.project;

import br.edu.ifsp.scl.pipegene.configuration.security.IAuthenticationFacade;
import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectDAO;
import br.edu.ifsp.scl.pipegene.web.exception.GenericResourceException;
import br.edu.ifsp.scl.pipegene.web.exception.ResourceForbiddenException;
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

    private final ProjectDAO projectDAO;
    private final ObjectStorageService objectStorageService;
    private final IAuthenticationFacade authentication;

    public ProjectCRUDImpl(ProjectDAO projectDAO, ObjectStorageService objectStorageService, IAuthenticationFacade authentication) {
        this.projectDAO = projectDAO;
        this.objectStorageService = objectStorageService;
        this.authentication = authentication;
    }

    @Override
    public Project createNewProject(String name, String description, List<MultipartFile> files) {
        List<Dataset> datasets = files.stream()
                .map(objectStorageService::putObject)
                .collect(Collectors.toList());

        return projectDAO.saveNewProject(name, description, datasets, authentication.getUserAuthenticatedId());
    }

    @Override
    public Project findProjectById(UUID projectId) {
        Optional<Project> optional = projectDAO.findProjectById(projectId);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        Project project = optional.get();
        verifyAccess(project.getOwnerId());

        return project;
    }

    @Override
    public Project updateProjectById(UUID projectId, ProjectUpdateRequest request) {
        Optional<Project> optional = projectDAO.findProjectById(projectId);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        Project project = optional.get();
        verifyAccess(project.getOwnerId());

        return projectDAO.updateProject(
                project.getUpdatedInstance(request)
        );
    }

    @Override
    public List<Project> findAllProjects() {
        return projectDAO.findAllProjects().stream()
                .filter(p -> p.getOwnerId().equals(authentication.getUserAuthenticatedId()))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteProjectById(UUID projectId) {
        Optional<Project> optional = projectDAO.findProjectById(projectId);

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }
        Project project = optional.get();
        verifyAccess(project.getOwnerId());

        if (!projectDAO.deleteProjectById(projectId)) {
            throw new GenericResourceException("The project cannot be deleted because exists process still using him", "Delete project not allowed");
        }

    }

    private void verifyAccess(UUID projectOwnerId) {
        if (!projectOwnerId.equals(authentication.getUserAuthenticatedId())) {
            throw new ResourceForbiddenException("You don't have access for this resource");
        }
    }
}
