package br.edu.ifsp.scl.pipegene.domain;

import java.util.UUID;

public class ExecutionStatus {
    private UUID id;
    private UUID projectId;
    private ExecutionStatusEnum status;

    private ExecutionStatus(UUID id, UUID projectId, ExecutionStatusEnum status) {
        this.id = id;
        this.projectId = projectId;
        this.status = status;
    }

    public static ExecutionStatus of(UUID id, UUID projectId, ExecutionStatusEnum status) {
        return new ExecutionStatus(id, projectId, status);
    }

    public UUID getId() {
        return id;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public ExecutionStatusEnum getStatus() {
        return status;
    }
}
