package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.Project;

import java.util.List;
import java.util.UUID;

public class ProjectEntity {

    private UUID id;
    private List<String> datasets;
    private String name;
    private String description;

    private ProjectEntity(UUID id, List<String> datasetUrl, String name, String description) {
        this.id = id;
        this.datasets = datasetUrl;
        this.name = name;
    }

    public static ProjectEntity of(UUID id, List<String> datasetUrl, String name, String description) {
        return new ProjectEntity(id, datasetUrl, name, description);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getDatasetUrl() {
        return datasets;
    }

    public void setDatasetUrl(List<String> datasets) {
        this.datasets = datasets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project toProject() {
        return Project.of(id, datasets, name, description);
    }
}
