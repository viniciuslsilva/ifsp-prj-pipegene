package br.edu.ifsp.scl.pipegene.web.model.provider.request;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

public class ProviderRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    //@URL
    private String url;

    @NotNull
    private Set<String> inputSupportedTypes;

    @NotNull
    private Set<String> outputSupportedTypes;

    @NotNull
    private Set<ProviderOperationDTO> operations;

    public ProviderRequest() {
    }

    public Provider convertToProvider() {
        return Provider.createWithoutId(name, description, url, inputSupportedTypes, outputSupportedTypes,
                operations.stream()
                        .map(ProviderOperationDTO::convertToProviderOperation)
                        .collect(Collectors.toList())
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getInputSupportedTypes() {
        return inputSupportedTypes;
    }

    public void setInputSupportedTypes(Set<String> inputSupportedTypes) {
        this.inputSupportedTypes = inputSupportedTypes;
    }

    public Set<String> getOutputSupportedTypes() {
        return outputSupportedTypes;
    }

    public void setOutputSupportedTypes(Set<String> outputSupportedTypes) {
        this.outputSupportedTypes = outputSupportedTypes;
    }

    public Set<ProviderOperationDTO> getOperations() {
        return operations;
    }

    public void setOperations(Set<ProviderOperationDTO> operations) {
        this.operations = operations;
    }
}
