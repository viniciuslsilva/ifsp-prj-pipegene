package br.edu.ifsp.scl.pipegene.domain;

import br.edu.ifsp.scl.pipegene.persistence.entities.ProviderEntity;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequestFlowDetails;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Provider {

    private UUID id;
    private List<String> inputSupportedTypes;
    private List<String> outputSupportedTypes;

    public Provider(UUID id, String inputSupportedType, String outputSupportedType) {
        this.id = id;
        this.inputSupportedTypes = Collections.singletonList(inputSupportedType);
        this.outputSupportedTypes = Collections.singletonList(outputSupportedType);
    }

    public static Provider from(ExecutionRequestFlowDetails e) {
        return new Provider(e.providerId, e.inputType, e.outputType);
    }

    public ProviderEntity toProviderEntity() {
        return new ProviderEntity(id, inputSupportedTypes, outputSupportedTypes);
    }
}
