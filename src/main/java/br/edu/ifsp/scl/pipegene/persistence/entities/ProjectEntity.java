package br.edu.ifsp.scl.pipegene.persistence.entities;

import java.util.UUID;

public class ProjectEntity {

    private UUID id;
    private String datasetUrl;
    private String name;

    public ProjectEntity(UUID id, String datasetUrl, String name) {
        this.id = id;
        this.datasetUrl = datasetUrl;
        this.name = name;
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
}
