package br.edu.ifsp.scl.pipegene.web.model.provider.request;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class ProviderRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    //@URL
    private String url;

    private Set<String> inputSupportedTypes;
    private Set<String> outputSupportedTypes;

    public ProviderRequest() {
    }

    public ProviderRequest(String name, String description, String url, Set<String> inputSupportedTypes, Set<String> outputSupportedTypes) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.inputSupportedTypes = inputSupportedTypes;
        this.outputSupportedTypes = outputSupportedTypes;
    }

    public Provider convertToProvider() {
        return Provider.createWithoutId(name, description, url, inputSupportedTypes, outputSupportedTypes);
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
}
