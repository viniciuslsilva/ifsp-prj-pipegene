package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.usecases.execution.queue.ExecutionQueueElement;
import br.edu.ifsp.scl.pipegene.web.model.provider.request.ProviderExecutionResultRequest;

import java.util.UUID;

public interface ExecutionTransaction {

    void startExecution(ExecutionQueueElement executionQueueElement);

    void validateNotificationFromProvider(UUID providerId, UUID executionId, UUID stepId);

    void processAsyncExecutionResult(UUID providerId, UUID executionId, UUID stepId, ProviderExecutionResultRequest providerExecutionResultRequest);
}
