package br.edu.ifsp.scl.pipegene.persistence.entities;

import java.util.UUID;

public class ExecutionStatusEntity {
    private UUID id;
    private UUID projectId;
    private ExecutionStatusEnum status;

    // TODO("Add attributes created_at and limit_date")

    public ExecutionStatusEntity() {
    }

    public ExecutionStatusEntity(UUID id, UUID projectId, ExecutionStatusEnum status) {
        this.id = id;
        this.projectId = projectId;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ExecutionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatusEnum status) {
        this.status = status;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
