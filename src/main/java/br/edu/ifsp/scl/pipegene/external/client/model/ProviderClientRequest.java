package br.edu.ifsp.scl.pipegene.external.client.model;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class ProviderClientRequest {
    private final UUID executionId;
    private final UUID stepId;
    private final String url;
    private final File file;
    private final Map<String, Object> params;

    public ProviderClientRequest(UUID executionId, UUID stepId, String url, File file, Map<String, Object> params) {
        this.executionId = executionId;
        this.stepId = stepId;
        this.url = url;
        this.file = file;
        this.params = params;
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

    public Map<String, Object> getParams() {
        return params;
    }
}
