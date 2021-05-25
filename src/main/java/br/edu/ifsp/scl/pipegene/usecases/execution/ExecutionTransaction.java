package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.usecases.execution.queue.ExecutionQueueElement;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderExecutionResultRequest;

import java.util.UUID;

public interface ExecutionTransaction {

    void start(ExecutionQueueElement executionQueueElement);

    void processExecutionResult(UUID providerId, UUID operationId, ProviderExecutionResultRequest providerExecutionResultRequest);
}
