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

    public static Provider createWithAllValues(UUID id, String name, String description, String url, Set<String> inputSupportedTypes, Set<String> outputSupportedTypes) {
        return new Provider(id, name, description, url, inputSupportedTypes, outputSupportedTypes);
    }

    public static Provider createWithoutId(String name, String description, String url, Set<String> inputSupportedTypes, Set<String> outputSupportedTypes) {
        return new Provider(null, name, description, url, inputSupportedTypes, outputSupportedTypes);
    }

    public static Provider createWithPartialValues(UUID id, String inputSupportedType, String outputSupportedType) {
        return new Provider(id, Set.of(inputSupportedType), Set.of(outputSupportedType));
    }

    public boolean isInputSupportedType(String inputType) {
        return inputSupportedTypes.contains(inputType);
    }

    public boolean isOutputSupportedType(String outputType) {
        return outputSupportedTypes.contains(outputType);
    }

    private Provider(UUID id, Set<String> inputSupportedType, Set<String> outputSupportedType) {
        this.id = id;
        this.inputSupportedTypes = inputSupportedType;
        this.outputSupportedTypes = outputSupportedType;
    }

    private Provider(UUID id, String name, String description, String url, Set<String> inputSupportedTypes, Set<String> outputSupportedTypes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.inputSupportedTypes = new HashSet<>(inputSupportedTypes);
        this.outputSupportedTypes = new HashSet<>(outputSupportedTypes);
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
