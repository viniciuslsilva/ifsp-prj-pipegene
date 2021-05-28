package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStepState;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionRepository;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.ExecutionQueueElement;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import br.edu.ifsp.scl.pipegene.usecases.provider.client.ProviderClient;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderRepository;
import br.edu.ifsp.scl.pipegene.usecases.provider.model.ProviderResponse;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequestFlowDetails;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderExecutionResultRequest;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderExecutionResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
public class ExecutionTransactionImpl implements ExecutionTransaction {

    private final Logger logger = LoggerFactory.getLogger(ExecutionTransactionImpl.class);
    private final ExecutionRepository executionRepository;
    private final ProviderRepository providerRepository;
    private final ProviderClient providerClient;
    private final ObjectStorageService storageService;

    public ExecutionTransactionImpl(ExecutionRepository executionRepository, ProviderRepository providerRepository, ProviderClient providerClient, ObjectStorageService storageService) {
        this.executionRepository = executionRepository;
        this.providerRepository = providerRepository;
        this.providerClient = providerClient;
        this.storageService = storageService;
    }

    @Override
    public void start(ExecutionQueueElement executionQueueElement) {
        Execution execution = executionRepository
                .findExecutionByExecutionId(executionQueueElement.getId())
                .orElseThrow();

        execution.setExecutionFlow(executionQueueElement.getExecutionRequestFlowDetails());
        logger.info(execution.toString());

        ExecutionRequestFlowDetails firstExecutionDetails = execution.getFirstExecutionDetails();
        Provider provider = providerRepository
                .findProviderById(firstExecutionDetails.getProviderId())
                .orElseThrow();

        validateExecutionDetailsWithProviderFound(provider, firstExecutionDetails);

        File file = storageService.getObject(execution.getDataset());

        logger.info("Sending queue element " + executionQueueElement.getId() + " to provider: " + provider.getId());

        callProviderClient(execution, provider, file);
        logger.info("Update execution in database");
        executionRepository.updateExecution(execution);
    }

    @Override
    public void processExecutionResult(UUID providerId, UUID operationId, ProviderExecutionResultRequest providerExecutionResultRequest) {
        Execution execution = executionRepository
                .findExecutionByExecutionId(operationId)
                .orElseThrow();

        if (!execution.getProviderFromCurrentExecution().equals(providerId)) {
            throw new IllegalArgumentException();
        }

        if (providerExecutionResultRequest.getStatus().equals(ProviderExecutionResultStatus.SUCCESS)) {
            execution.setCurrentExecutionState(ExecutionStepState.SUCCESS);

            Provider provider = providerRepository.findProviderById(providerId).orElseThrow();

            if (execution.hasNextExecution()) {
                applyNextExecution(execution, providerExecutionResultRequest, provider);
            } else {
                execution.finishExecution();
                executionRepository.updateExecution(execution);
            }
        }
    }

    private void applyNextExecution(Execution execution, ProviderExecutionResultRequest providerExecutionResultRequest, Provider provider) {
        ExecutionRequestFlowDetails nextExecutionDetails = execution.getNextExecutionDetails();
        Provider nextProvider = providerRepository
                .findProviderById(nextExecutionDetails.getProviderId())
                .orElseThrow();

        File fileToSend = null;
        try {
            URI uri = provider.buildDownloadURI(providerExecutionResultRequest.getFilename());
            Resource file = providerClient.getFile(uri);

            Path tempFile = Files.createTempFile(null, null);
            Files.write(tempFile, file.getInputStream().readAllBytes());
            fileToSend = tempFile.toFile();

            validateExecutionDetailsWithProviderFound(nextProvider, nextExecutionDetails);
            callProviderClient(execution, nextProvider, fileToSend);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(fileToSend)) {
                fileToSend.delete();
            }
        }

        executionRepository.updateExecution(execution);
    }

    private void callProviderClient(Execution execution, Provider provider, File file) {
        ProviderResponse endpointToCheck = providerClient.postFile(execution.getId(), provider.getURI(), file);

        // TODO adiccionar logica para tempo maximo de processamento
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
