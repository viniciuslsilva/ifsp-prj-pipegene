package br.edu.ifsp.scl.pipegene.web.model.project;

import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.web.model.DatasetDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProjectResponse {

    private UUID id;
    private List<DatasetDTO> datasets;
    private String name;
    private String description;

    private ProjectResponse() { }

    private ProjectResponse(UUID id, List<DatasetDTO> datasets, String name, String description) {
        this.id = id;
        this.datasets = datasets;
        this.name = name;
        this.description = description;
    }

    public static ProjectResponse createFromProject(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getDatasets().stream().map(DatasetDTO::createFromDataset).collect(Collectors.toList()),
                project.getName(),
                project.getDescription()
        );
    }

    public UUID getId() {
        return id;
    }

    public List<DatasetDTO> getDatasets() {
        return datasets;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
