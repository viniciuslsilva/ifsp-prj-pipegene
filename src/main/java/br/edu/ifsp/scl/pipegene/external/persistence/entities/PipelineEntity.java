package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PipelineEntity {
    private UUID id;
    private UUID projectId;
    private String description;
    private List<ExecutionStepEntity> steps;

    public PipelineEntity() {
    }

    private PipelineEntity(UUID id, UUID projectId, String description, List<ExecutionStepEntity> steps) {
        this.id = id;
        this.projectId = projectId;
        this.description = description;
        this.steps = new ArrayList<>(steps);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

}
