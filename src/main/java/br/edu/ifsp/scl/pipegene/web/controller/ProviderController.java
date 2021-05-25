package br.edu.ifsp.scl.pipegene.web.controller;

import br.edu.ifsp.scl.pipegene.usecases.execution.ExecutionTransaction;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderExecutionResultRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProviderController {

    private final ExecutionTransaction executionTransaction;

    public ProviderController(ExecutionTransaction executionTransaction) {
        this.executionTransaction = executionTransaction;
    }

    @PostMapping("v1/providers/{providerId}/operations/{operationId}")
    public ResponseEntity<?> notifyExecutionResult(
            @PathVariable UUID providerId,
            @PathVariable UUID operationId,
            @RequestBody ProviderExecutionResultRequest providerExecutionResultRequest
    ) {

        executionTransaction.processExecutionResult(providerId, operationId, providerExecutionResultRequest);

        return  ResponseEntity.ok("");
    }
}
