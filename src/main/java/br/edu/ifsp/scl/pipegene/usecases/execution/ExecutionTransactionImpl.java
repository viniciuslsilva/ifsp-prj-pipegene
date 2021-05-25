package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStatus;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStepState;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionRepository;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.ExecutionQueueElement;
import br.edu.ifsp.scl.pipegene.usecases.provider.client.ProviderClient;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderRepository;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequestFlowDetails;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderExecutionResultRequest;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderExecutionResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExecutionTransactionImpl implements ExecutionTransaction {

    private final Logger logger = LoggerFactory.getLogger(ExecutionTransactionImpl.class);
    private final ExecutionRepository executionRepository;
    private final ProviderRepository providerRepository;
    private final ProviderClient providerClient;

    public ExecutionTransactionImpl(ExecutionRepository executionRepository, ProviderRepository providerRepository, ProviderClient providerClient) {
        this.executionRepository = executionRepository;
        this.providerRepository = providerRepository;
        this.providerClient = providerClient;
    }

    @Override
    public void start(ExecutionQueueElement executionQueueElement) {
        ExecutionStatus executionStatus = executionRepository
                .findExecutionStatusByExecutionId(executionQueueElement.getId())
                .orElseThrow();

        executionStatus.setExecutionFlow(executionQueueElement.getExecutionRequestFlowDetails());
        logger.info(executionStatus.toString());

        ExecutionRequestFlowDetails firstExecutionDetails = executionStatus.getFirstExecutionDetails();
        Provider provider = providerRepository
                .findProviderById(firstExecutionDetails.getProviderId())
                .orElseThrow();

        validateExecutionDetailsWithProviderFound(provider, firstExecutionDetails);
        applyOperations(executionStatus, provider);

    }

    @Override
    public void processExecutionResult(UUID providerId, UUID operationId, ProviderExecutionResultRequest providerExecutionResultRequest) {
        ExecutionStatus executionStatus = executionRepository
                .findExecutionStatusByExecutionId(operationId)
                .orElseThrow();

        if (!executionStatus.getProviderFromCurrentExecution().equals(providerId)) {
            throw new IllegalArgumentException();
        }

        if (providerExecutionResultRequest.getStatus().equals(ProviderExecutionResultStatus.SUCCESS)) {
            executionStatus.setCurrentExecutionState(ExecutionStepState.SUCCESS);
            ExecutionRequestFlowDetails nextExecutionDetails = executionStatus.getNextExecutionDetails();
            Provider provider = providerRepository
                    .findProviderById(nextExecutionDetails.getProviderId())
                    .orElseThrow();

            validateExecutionDetailsWithProviderFound(provider, nextExecutionDetails);
            applyOperations(executionStatus, provider);
        }
    }

    private void applyOperations(ExecutionStatus executionStatus, Provider provider) {
        providerClient.postFile(executionStatus.getId(), provider, executionStatus.getProject().getDatasetUrl());
        executionRepository.updateExecutionStatus(executionStatus);
    }

    private void validateExecutionDetailsWithProviderFound(Provider provider, ExecutionRequestFlowDetails executionDetails) {
        if (!provider.isInputSupportedType(executionDetails.getInputType())) {
            throw new IllegalArgumentException();
        }

        if (!provider.isOutputSupportedType(executionDetails.getOutputType())) {
            throw new IllegalArgumentException();
        }
    }


}
