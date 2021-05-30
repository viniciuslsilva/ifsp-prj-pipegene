package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.project.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("v1/projects")
    public ResponseEntity<Project> createNewProject(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("files") List<MultipartFile> files) {
        Project project = projectService.createNewProject(name, description, files);
        return ResponseEntity.ok(project);
    }

    @GetMapping("v1/projects/")
    public ResponseEntity<?> listAllProjects() {
        List<Project> projects = projectService.findAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("v1/projects/{projectId}")
    public ResponseEntity<Project> findProjectById(@PathVariable UUID projectId) {
        Project project = projectService.findProjectById(projectId);
        return ResponseEntity.ok(project);
    }
}
