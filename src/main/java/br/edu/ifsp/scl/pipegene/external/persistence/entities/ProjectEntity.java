package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.Project;

import java.util.UUID;

public class ProjectEntity {

    private UUID id;
    private String datasetUrl;
    private String name;

    private ProjectEntity(UUID id, String datasetUrl, String name) {
        this.id = id;
        this.datasetUrl = datasetUrl;
        this.name = name;
    }

    public static ProjectEntity of(UUID id, String datasetUrl, String name) {
        return new ProjectEntity(id, datasetUrl, name);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDatasetUrl() {
        return datasetUrl;
    }

    public void setDatasetUrl(String datasetUrl) {
        this.datasetUrl = datasetUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project toProject() {
        return Project.of(id, datasetUrl, name);
    }
}
