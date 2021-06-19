package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.project.ProjectCRUD;
import br.edu.ifsp.scl.pipegene.web.model.project.ProjectResponse;
import br.edu.ifsp.scl.pipegene.web.model.project.ProjectUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/projects")
@RestController
public class ProjectController {

    private final ProjectCRUD projectCRUD;

    public ProjectController(ProjectCRUD projectCRUD) {
        this.projectCRUD = projectCRUD;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createNewProject(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("files") List<MultipartFile> files) {
        Project project = projectCRUD.createNewProject(name, description, files);

        return ResponseEntity.ok(ProjectResponse.createFromProject(project));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> listAllProjects() {
        List<Project> projects = projectCRUD.findAllProjects();

        return ResponseEntity.ok(
                projects.stream()
                        .map(ProjectResponse::createFromProject)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> findProjectById(@PathVariable UUID projectId) {
        Project project = projectCRUD.findProjectById(projectId);

        return ResponseEntity.ok(ProjectResponse.createFromProject(project));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProjectById(@PathVariable UUID projectId,
                                                             @RequestBody @Valid ProjectUpdateRequest request) {
        Project project = projectCRUD.updateProjectById(projectId, request);

        return ResponseEntity.ok(ProjectResponse.createFromProject(project));
    }
}
