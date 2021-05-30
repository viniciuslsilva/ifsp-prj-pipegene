package br.edu.ifsp.scl.pipegene.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Provider {

    private UUID id;
    private String name;
    private String description;
    private String url;
    private Set<String> inputSupportedTypes;
    private Set<String> outputSupportedTypes;

    private Provider(UUID id, String inputSupportedType, String outputSupportedType) {
        this.id = id;
        this.inputSupportedTypes = Set.of(inputSupportedType);
        this.outputSupportedTypes = Set.of(outputSupportedType);
    }

    public Provider(UUID id, String name, String description, String url, Set<String> inputSupportedTypes, Set<String> outputSupportedTypes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.inputSupportedTypes = new HashSet<>(inputSupportedTypes);
        this.outputSupportedTypes = new HashSet<>(outputSupportedTypes);
    }

    public static Provider of(UUID id, String name, String description, String url, Set<String> inputSupportedTypes, Set<String> outputSupportedTypes) {
        return new Provider(id, name, description, url, inputSupportedTypes, outputSupportedTypes);
    }

    public static Provider of(UUID id, String inputSupportedType, String outputSupportedType) {
        return new Provider(id, inputSupportedType, outputSupportedType);
    }

    public boolean isInputSupportedType(String inputType) {
        return inputSupportedTypes.contains(inputType);
    }

    public boolean isOutputSupportedType(String outputType) {
        return outputSupportedTypes.contains(outputType);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getInputSupportedTypes() {
        return new HashSet<>(inputSupportedTypes);
    }

    public Set<String> getOutputSupportedTypes() {
        return new HashSet<>(outputSupportedTypes);
    }
}
