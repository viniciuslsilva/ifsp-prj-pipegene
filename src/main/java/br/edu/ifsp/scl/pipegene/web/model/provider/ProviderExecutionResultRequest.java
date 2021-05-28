package br.edu.ifsp.scl.pipegene.web.model.provider;

public class ProviderExecutionResultRequest {

    private ProviderExecutionResultStatus status;
    private String filename;
    // TODO Add error fields


    public ProviderExecutionResultStatus getStatus() {
        return status;
    }

    public String getFilename() {
        return filename;
    }
}
