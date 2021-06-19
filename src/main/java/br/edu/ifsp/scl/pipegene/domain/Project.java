package br.edu.ifsp.scl.pipegene.domain;

import br.edu.ifsp.scl.pipegene.web.model.project.ProjectUpdateRequest;

import java.util.*;

public class Project {

    private UUID id;
    private String name;
    private String description;
    private List<Dataset> datasets;
    private List<Pipeline> pipelines;
    private UUID ownerId;

    public boolean hasDataset(UUID datasetId) {
        return datasets.stream().anyMatch(d -> d.getId().equals(datasetId));
    }

    public boolean hasPipeline(UUID pipelineId) {
        return pipelines.stream().anyMatch(p -> p.getId().equals(pipelineId));
    }

    public Pipeline findPipelineById(UUID pipelineId) {
        return pipelines.stream()
                .filter(p -> p.getId().equals(pipelineId)).findFirst().orElseThrow();
    }

    public Dataset findDatasetById(UUID datasetId) {
        return datasets.stream()
                .filter(d -> d.getId().equals(datasetId)).findFirst().orElseThrow();
    }

    public void addDataset(Dataset dataset) {
        this.datasets.add(dataset);
    }

    public void addPipeline(List<Pipeline> pipelines) {
        this.pipelines.addAll(pipelines);
    }

    private Project(UUID id, List<Dataset> datasets, String name, String description, List<Pipeline> pipelines, UUID ownerId) {
        this.id = id;
        this.datasets = datasets;
        this.name = name;
        this.description = description;
        this.pipelines = pipelines;
        this.ownerId = ownerId;
    }

    public static Project of(UUID id, List<Dataset> datasets, String name, String description, List<Pipeline> pipelines,
                             UUID ownerId) {
        List<Dataset> datasetList = Objects.isNull(datasets) ? new ArrayList<>() : new ArrayList<>(datasets);
        List<Pipeline> pipelineList = Objects.isNull(pipelines) ? new ArrayList<>() : new ArrayList<>(pipelines);
        return new Project(id, datasetList, name, description, pipelineList, ownerId);
    }

    private Project(UUID id) {
        this.id = id;
    }

    public static Project createWithOnlyId(UUID id) {
        return new Project(id);
    }

    public UUID getId() {
        return id;
    }

    public List<Dataset> getDatasets() {
        return new ArrayList<>(datasets);
    }

    public List<Pipeline> getPipelines() {
        return new ArrayList<>(pipelines);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Project getUpdatedInstance(ProjectUpdateRequest request) {
        String newName = Objects.nonNull(request.getName()) ? request.getName() : name;
        String newDescription = Objects.nonNull(request.getDescription()) ? request.getDescription() : description;

        return new Project(
                id,
                datasets,
                newName,
                newDescription,
                pipelines,
                ownerId
        );
    }

    public UUID getOwnerId() {
        return ownerId;
    }
}
