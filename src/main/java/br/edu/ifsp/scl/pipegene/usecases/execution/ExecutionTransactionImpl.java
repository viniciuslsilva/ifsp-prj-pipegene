package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStep;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStepState;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionRepository;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.ExecutionQueueElement;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderClient;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderRepository;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionStepRequest;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderExecutionResultRequest;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderExecutionResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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

        List<ExecutionStep> steps = mapToExecutionStep(executionQueueElement.getExecutionStepRequests());
        execution.setExecutionSteps(steps);

        logger.info(execution.toString());

        ExecutionStep executionStep = execution.getFirstExecutionStep();
        Provider provider = providerRepository
                .findProviderById(executionStep.getProviderId())
                .orElseThrow();

        validateExecutionDetailsWithProviderFound(provider, executionStep);

        File file = storageService.getObject(execution.getDataset());

        logger.info("Sending queue element " + executionQueueElement.getId() + " to provider: " + provider.getId());

        callProviderClient(execution, provider, file);
        logger.info("Update execution in database");
        executionRepository.updateExecution(execution);
    }

    private List<ExecutionStep> mapToExecutionStep(List<ExecutionStepRequest> executionRequestFlowDetails) {
        return executionRequestFlowDetails
                .stream()
                .map(e -> ExecutionStep.of(e.getProviderId(), e.getInputType(), e.getOutputType(), ExecutionStepState.NOT_EXECUTED))
                .collect(Collectors.toList());
    }

    @Override
    public void validateNotificationFromProvider(UUID providerId, UUID executionId, UUID stepId) {
        Execution execution = executionRepository
                .findExecutionByExecutionIdAndCurrentExecutionStepId(executionId, stepId)
                .orElseThrow();

        if (!execution.getProviderIdFromCurrentExecutionStep().equals(providerId)) {
            throw new IllegalArgumentException();
        }
    }

    @Async
    @Override
    public void processAsyncExecutionResult(UUID providerId, UUID executionId, UUID stepId, ProviderExecutionResultRequest providerExecutionResultRequest) {
        Execution execution = executionRepository
                .findExecutionByExecutionIdAndCurrentExecutionStepId(executionId, stepId)
                .orElseThrow();

        if (!execution.getProviderIdFromCurrentExecutionStep().equals(providerId)) {
            throw new IllegalArgumentException();
        }

        if (providerExecutionResultRequest.getStatus().equals(ProviderExecutionResultStatus.SUCCESS)) {
            execution.setCurrentExecutionStepState(ExecutionStepState.SUCCESS);
            if (execution.hasNextExecution()) {
                applyNextExecution(execution, providerExecutionResultRequest);
            } else {
                execution.finishExecution(providerExecutionResultRequest.getUri());
                executionRepository.updateExecution(execution);
            }
        }
    }

    private void applyNextExecution(Execution execution, ProviderExecutionResultRequest providerExecutionResultRequest) {
        ExecutionStep executionStep = execution.getNextExecutionStep();
        Provider nextProvider = providerRepository
                .findProviderById(executionStep.getProviderId())
                .orElseThrow();

        File fileToSend = null;
        try {
            URI uri = providerExecutionResultRequest.getUri();
            Resource file = providerClient.retrieveProcessedFileRequest(uri);

            Path tempFile = Files.createTempFile(null, null);
            Files.write(tempFile, file.getInputStream().readAllBytes());
            fileToSend = tempFile.toFile();

            validateExecutionDetailsWithProviderFound(nextProvider, executionStep);
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
        try {
            providerClient.processRequest(execution.getId(), execution.getStepIdFromCurrentExecutionStep(), provider.getUrl(), file);
            execution.setCurrentExecutionStepState(ExecutionStepState.IN_PROGRESS);
        } catch (Exception e) {
            execution.setCurrentExecutionStepState(ExecutionStepState.ERROR);
        }
        // TODO adiccionar logica para tempo maximo de processamento
    }

    private void validateExecutionDetailsWithProviderFound(Provider provider, ExecutionStep executionStep) {
        if (!provider.isInputSupportedType(executionStep.getInputType())) {
            throw new IllegalArgumentException();
        }

        if (!provider.isOutputSupportedType(executionStep.getOutputType())) {
            throw new IllegalArgumentException();
        }
    }
}
