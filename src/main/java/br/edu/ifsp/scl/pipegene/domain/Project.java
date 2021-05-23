package br.edu.ifsp.scl.pipegene.domain;

import java.util.UUID;

public class Project {

    private UUID id;
    private String datasetUrl;
    private String name;

    private Project(UUID id, String datasetUrl, String name) {
        this.id = id;
        this.datasetUrl = datasetUrl;
        this.name = name;
    }

    public static Project of(UUID id, String datasetUrl, String name) {
        return new Project(id, datasetUrl, name);
    }

    public UUID getId() {
        return id;
    }

    public String getDatasetUrl() {
        return datasetUrl;
    }

    public String getName() {
        return name;
    }


}
