package br.edu.ifsp.scl.pipegene.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.*;

public class ProviderEntity {
    private UUID id;
    private Set<String> inputSupportedTypes;
    private Set<String> outputSupportedTypes;
    private String name;
    private String providerUrl;

    private ProviderEntity(UUID id, Collection<String> inputSupportedTypes, Collection<String> outputSupportedTypes) {
        this.id = id;
        this.inputSupportedTypes = new HashSet<>(inputSupportedTypes);
        this.outputSupportedTypes = new HashSet<>(outputSupportedTypes);
    }

    public static ProviderEntity of(UUID id, Collection<String> inputSupportedTypes, Collection<String> outputSupportedTypes) {
        return new ProviderEntity(id, inputSupportedTypes, outputSupportedTypes);
    }

    public Provider toProvider() {
        return Provider.of(id, inputSupportedTypes, outputSupportedTypes);
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

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }
}
