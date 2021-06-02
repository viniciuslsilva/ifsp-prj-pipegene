package br.edu.ifsp.scl.pipegene.external.client.model;

import br.edu.ifsp.scl.pipegene.domain.Operation;

import java.io.File;
import java.util.UUID;

public class ProviderClientRequest {
    private UUID executionId;
    private UUID stepId;
    private String url;
    private File file;
    private Operation operation;

    public ProviderClientRequest() {
    }

    public ProviderClientRequest(UUID executionId, UUID stepId, String url, File file, Operation operation) {
        this.executionId = executionId;
        this.stepId = stepId;
        this.url = url;
        this.file = file;
        this.operation = operation;
    }

    public UUID getExecutionId() {
        return executionId;
    }

    public UUID getStepId() {
        return stepId;
    }

    public String getUrl() {
        return url;
    }

    public File getFile() {
        return file;
    }

    public Operation getOperation() {
        return operation;
    }
}
