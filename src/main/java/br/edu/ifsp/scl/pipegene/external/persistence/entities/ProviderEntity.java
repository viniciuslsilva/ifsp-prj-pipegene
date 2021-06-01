package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.*;

public class ProviderEntity {
    private UUID id;
    private String name;
    private String description;
    private String url;
    private Set<String> inputSupportedTypes;
    private Set<String> outputSupportedTypes;


    private ProviderEntity(UUID id, String name, String description, String url, Collection<String> inputSupportedTypes, Collection<String> outputSupportedTypes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.inputSupportedTypes = new HashSet<>(inputSupportedTypes);
        this.outputSupportedTypes = new HashSet<>(outputSupportedTypes);
    }

    public static ProviderEntity of(UUID id, String name, String description, String url, Collection<String> inputSupportedTypes, Collection<String> outputSupportedTypes) {
        return new ProviderEntity(id, name, description, url, inputSupportedTypes, outputSupportedTypes);
    }

    public static ProviderEntity createFromProviderWithoutId(Provider provider) {
        return new ProviderEntity(UUID.randomUUID(), provider.getName(), provider.getDescription(), provider.getUrl(),
                provider.getInputSupportedTypes(), provider.getOutputSupportedTypes());
    }

    public Provider convertToProvider() {
        return Provider.createWithAllValues(id, name, description, url, inputSupportedTypes, outputSupportedTypes);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
