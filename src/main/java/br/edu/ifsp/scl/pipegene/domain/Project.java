package br.edu.ifsp.scl.pipegene.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {

    private UUID id;
    private List<String> datasets;
    private String name;
    private String description;

    public boolean hasDataset(String dataset) {
        return datasets.contains(dataset);
    }

    private Project(UUID id, List<String> datasets, String name, String description) {
        this.id = id;
        this.datasets = datasets;
        this.name = name;
        this.description = description;
    }

    public static Project of(UUID id, List<String> datasetUrl, String name, String description) {
        return new Project(id, datasetUrl, name, description);
    }

    public UUID getId() {
        return id;
    }

    public List<String> getDatasets() {
        return new ArrayList<>(datasets);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
