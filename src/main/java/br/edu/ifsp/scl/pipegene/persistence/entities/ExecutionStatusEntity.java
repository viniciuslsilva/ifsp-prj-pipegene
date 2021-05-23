package br.edu.ifsp.scl.pipegene.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStatus;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStatusEnum;

import java.util.UUID;

public class ExecutionStatusEntity {
    private UUID id;
    private UUID projectId;
    private String status;

    // TODO("Add attributes created_at and limit_date")

    private ExecutionStatusEntity(UUID id, UUID projectId, String status) {
        this.id = id;
        this.projectId = projectId;
        this.status = status;
    }

    public static ExecutionStatusEntity of(ExecutionStatus executionStatus) {
        return new ExecutionStatusEntity(
                executionStatus.getId(),
                executionStatus.getProjectId(),
                executionStatus.getStatus().name()
        );
    }

    public ExecutionStatus toExecutionStatus() {
        return ExecutionStatus.of(id, projectId, ExecutionStatusEnum.valueOf(status));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
