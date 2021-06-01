package br.edu.ifsp.scl.pipegene.web.model.provider.response;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.Set;
import java.util.UUID;

public class ProviderResponse {

    private UUID id;
    private String name;
    private String description;
    private String url;
    private Set<String> inputSupportedTypes;
    private Set<String> outputSupportedTypes;

    public static ProviderResponse createFromProvider(Provider p) {
        return new ProviderResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getUrl(),
                p.getInputSupportedTypes(),
                p.getOutputSupportedTypes()
        );
    }

    private ProviderResponse() { }

    private ProviderResponse(UUID id, String name, String description, String url, Set<String> inputSupportedTypes, Set<String> outputSupportedTypes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.inputSupportedTypes = inputSupportedTypes;
        this.outputSupportedTypes = outputSupportedTypes;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Set<String> getInputSupportedTypes() {
        return inputSupportedTypes;
    }

    public Set<String> getOutputSupportedTypes() {
        return outputSupportedTypes;
    }
}
