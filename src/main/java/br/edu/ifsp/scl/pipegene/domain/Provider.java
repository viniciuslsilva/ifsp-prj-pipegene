package br.edu.ifsp.scl.pipegene.domain;

import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequestFlowDetails;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Provider {

    private UUID id;
    private Set<String> inputSupportedTypes;
    private Set<String> outputSupportedTypes;

    private Provider(UUID id, String inputSupportedType, String outputSupportedType) {
        this.id = id;
        this.inputSupportedTypes = Set.of(inputSupportedType);
        this.outputSupportedTypes = Set.of(outputSupportedType);
    }

    private Provider(UUID id, Set<String> inputSupportedType, Set<String> outputSupportedType) {
        this.id = id;
        this.inputSupportedTypes = new HashSet<>(inputSupportedType);
        this.outputSupportedTypes = new HashSet<>(outputSupportedType);
    }

    public static Provider of(UUID id, Set<String> inputSupportedType, Set<String> outputSupportedType) {
        return new Provider(id, inputSupportedType, outputSupportedType);
    }


    public static Provider fromExecutionRequestFlowDetails(ExecutionRequestFlowDetails e) {
        return new Provider(e.providerId, e.inputType, e.outputType);
    }

    public UUID getId() {
        return id;
    }
}
