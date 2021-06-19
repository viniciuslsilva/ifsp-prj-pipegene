package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.domain.Project;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProjectEntity {

    private UUID id;
    private List<DatasetEntity> datasets;
    private String name;
    private String description;

    public static ProjectEntity createEntityInstance(UUID id, List<Dataset> datasets, String name, String description) {
        List<DatasetEntity> datasetIds= datasets.stream().map(DatasetEntity::createFromDataset).collect(Collectors.toList());
        return new ProjectEntity(id, datasetIds, name, description);
    }

    public static ProjectEntity createEntityInstance(Project project) {
        List<DatasetEntity> datasetIds= project.getDatasets().stream().map(DatasetEntity::createFromDataset).collect(Collectors.toList());
        return new ProjectEntity(project.getId(), datasetIds, project.getName(), project.getDescription());
    }

    private ProjectEntity(UUID id, List<DatasetEntity> datasets, String name, String description) {
        this.id = id;
        this.datasets = datasets;
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<DatasetEntity> getDatasets() {
        return datasets;
    }

    public void setDataset(List<DatasetEntity> datasets) {
        this.datasets = datasets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
