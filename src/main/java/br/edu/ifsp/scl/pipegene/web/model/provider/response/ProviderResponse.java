package br.edu.ifsp.scl.pipegene.web.model.provider.response;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.web.model.provider.request.ProviderOperationDTO;

import java.util.*;
import java.util.stream.Collectors;

public class ProviderResponse {

    private UUID id;
    private String name;
    private String description;
    private String url;
    private Collection<String> inputSupportedTypes;
    private Collection<String> outputSupportedTypes;
    private Collection<ProviderOperationDTO> operations;

    public static ProviderResponse createFromProvider(Provider p) {
        return new ProviderResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getUrl(),
                p.getInputSupportedTypes(),
                p.getOutputSupportedTypes(),
                p.getOperations().stream()
                        .map(ProviderOperationDTO::createFromProviderOperation)
                        .collect(Collectors.toList())
        );
    }

    private ProviderResponse() { }

    private ProviderResponse(UUID id, String name, String description, String url, Collection<String> inputSupportedTypes,
                             Collection<String> outputSupportedTypes, Collection<ProviderOperationDTO> operations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.inputSupportedTypes = Collections.unmodifiableCollection(inputSupportedTypes);
        this.outputSupportedTypes = Collections.unmodifiableCollection(outputSupportedTypes);
        this.operations = Collections.unmodifiableCollection(operations);
    }

    public static ProviderResponse createWithPartialValuesFromProvider(Provider p) {
        return new ProviderResponse(p.getId(), p.getName(), p.getDescription());
    }

    public ProviderResponse(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public Collection<String> getInputSupportedTypes() {
        return inputSupportedTypes;
    }

    public Collection<String> getOutputSupportedTypes() {
        return outputSupportedTypes;
    }

    public Collection<ProviderOperationDTO> getOperations() {
        return operations;
    }
}
