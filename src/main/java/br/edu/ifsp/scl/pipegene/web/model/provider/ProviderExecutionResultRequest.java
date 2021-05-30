package br.edu.ifsp.scl.pipegene.web.model.provider;

import java.net.URI;

public class ProviderExecutionResultRequest {

    private ProviderExecutionResultStatus status;

    private URI uri;
    // TODO Add error fields

    public ProviderExecutionResultStatus getStatus() {
        return status;
    }

    public URI getUri() {
        return uri;
    }
}
