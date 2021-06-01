package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.project.ProjectService;
import br.edu.ifsp.scl.pipegene.web.model.project.ProjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("v1/projects")
    public ResponseEntity<ProjectResponse> createNewProject(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("files") List<MultipartFile> files) {
        Project project = projectService.createNewProject(name, description, files);

        return ResponseEntity.ok(ProjectResponse.createFromProject(project));
    }

    @GetMapping("v1/projects/")
    public ResponseEntity<List<ProjectResponse>> listAllProjects() {
        List<Project> projects = projectService.findAllProjects();

        return ResponseEntity.ok(
                projects.stream()
                        .map(ProjectResponse::createFromProject)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("v1/projects/{projectId}")
    public ResponseEntity<ProjectResponse> findProjectById(@PathVariable UUID projectId) {
        Project project = projectService.findProjectById(projectId);

        return ResponseEntity.ok(ProjectResponse.createFromProject(project));
    }
}
