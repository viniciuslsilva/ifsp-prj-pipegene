package br.edu.ifsp.scl.pipegene.domain;

import java.util.*;

public class Project {

    private UUID id;
    private List<Dataset> datasets;
    private String name;
    private String description;

    public boolean hasDataset(UUID datasetId) {
        return datasets.stream().anyMatch(d -> d.getId().equals(datasetId));
    }

    public Dataset findDatasetById(UUID datasetId) {
        return datasets.stream()
                .filter(d -> d.getId().equals(datasetId)).findFirst().orElseThrow();
    }

    private Project(UUID id, List<Dataset> datasets, String name, String description) {
        this.id = id;
        this.datasets = datasets;
        this.name = name;
        this.description = description;
    }

    public static Project of(UUID id, List<Dataset> datasets, String name, String description) {
        List<Dataset> datasetList = Objects.isNull(datasets) ? Collections.emptyList() : new ArrayList<>(datasets);
        return new Project(id, datasetList, name, description);
    }

    public UUID getId() {
        return id;
    }

    public List<Dataset> getDatasets() {
        return new ArrayList<>(datasets);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
