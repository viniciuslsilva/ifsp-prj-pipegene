package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.domain.*;
import br.edu.ifsp.scl.pipegene.external.client.model.ProviderClientRequest;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionDAO;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.ExecutionQueueElement;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderClient;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderDAO;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionStepRequest;
import br.edu.ifsp.scl.pipegene.web.model.provider.request.ProviderExecutionResultRequest;
import br.edu.ifsp.scl.pipegene.web.model.provider.request.ProviderExecutionResultStatus;
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
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExecutionTransactionImpl implements ExecutionTransaction {

    private final Logger logger = LoggerFactory.getLogger(ExecutionTransactionImpl.class);
    private final ExecutionDAO executionDAO;
    private final ProviderDAO providerDAO;
    private final ProviderClient providerClient;
    private final ObjectStorageService storageService;

    public ExecutionTransactionImpl(ExecutionDAO executionDAO, ProviderDAO providerDAO, ProviderClient providerClient, ObjectStorageService storageService) {
        this.executionDAO = executionDAO;
        this.providerDAO = providerDAO;
        this.providerClient = providerClient;
        this.storageService = storageService;
    }

    @Override
    public void startExecution(ExecutionQueueElement executionQueueElement) {
        Execution execution = executionDAO
                .findExecutionByExecutionId(executionQueueElement.getId())
                .orElseThrow();

        List<ExecutionStep> steps = mapToExecutionStep(executionQueueElement.getExecutionStepRequests());
        execution.setExecutionSteps(steps);

        logger.info(execution.toString());

        ExecutionStep executionStep = execution.getFirstExecutionStep();
        Provider provider = providerDAO
                .findProviderById(executionStep.getProviderId())
                .orElseThrow();

        validateExecutionDetailsWithProviderFound(provider, executionStep);

        File file = storageService.getObject(execution.getDataset());

        logger.info("Sending queue element " + executionQueueElement.getId() + " to provider: " + provider.getId());

        callProviderClient(execution, executionStep.getExecutionStepParams(), provider, file);
        logger.info("Update execution in database");
        executionDAO.updateExecution(execution);
    }

    private List<ExecutionStep> mapToExecutionStep(List<ExecutionStepRequest> executionStepRequest) {
        return executionStepRequest
                .stream()
                .map(e -> ExecutionStep.createGeneratingStepId(e.getProviderId(), e.getInputType(), e.getOutputType(),
                        ExecutionStepState.NOT_EXECUTED, e.getParams()))
                .collect(Collectors.toList());
    }

    @Override
    public void validateNotificationFromProvider(UUID providerId, UUID executionId, UUID stepId) {
        Execution execution = executionDAO
                .findExecutionByExecutionIdAndCurrentExecutionStepId(executionId, stepId)
                .orElseThrow();

        if (!execution.getProviderIdFromCurrentExecutionStep().equals(providerId)) {
            throw new IllegalArgumentException();
        }
    }

    @Async
    @Override
    public void processAsyncExecutionResult(UUID providerId, UUID executionId, UUID stepId, ProviderExecutionResultRequest providerExecutionResultRequest) {
        Execution execution = executionDAO
                .findExecutionByExecutionIdAndCurrentExecutionStepId(executionId, stepId)
                .orElseThrow();

        if (!execution.getProviderIdFromCurrentExecutionStep().equals(providerId)) {
            throw new IllegalArgumentException();
        }

        if (providerExecutionResultRequest.getStatus().equals(ProviderExecutionResultStatus.SUCCESS)) {
            execution.setCurrentExecutionStepState(ExecutionStepState.SUCCESS);
            if (execution.hasNextExecutionStep()) {
                applyNextExecution(execution, providerExecutionResultRequest);
            } else {
                execution.finishExecution(providerExecutionResultRequest.getUri());
                executionDAO.updateExecution(execution);
            }
        }
    }

    private void applyNextExecution(Execution execution, ProviderExecutionResultRequest providerExecutionResultRequest) {
        ExecutionStep executionStep = execution.getNextExecutionStep();
        Provider provider = providerDAO
                .findProviderById(executionStep.getProviderId())
                .orElseThrow();

        File fileToSend = null;
        try {
            URI uri = providerExecutionResultRequest.getUri();
            Resource file = providerClient.retrieveProcessedFileRequest(uri);

            Path tempFile = Files.createTempFile(null, null);
            Files.write(tempFile, file.getInputStream().readAllBytes());
            fileToSend = tempFile.toFile();

            validateExecutionDetailsWithProviderFound(provider, executionStep);
            callProviderClient(execution, executionStep.getExecutionStepParams(), provider, fileToSend);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(fileToSend)) {
                fileToSend.delete();
            }
        }

        executionDAO.updateExecution(execution);
    }

    private void callProviderClient(Execution execution, Map<String, Object> executionStepParams, Provider provider, File file) {
        try {
            ProviderClientRequest request = new ProviderClientRequest(
                    execution.getId(), execution.getStepIdFromCurrentExecutionStep(), provider.getUrl(), file,
                    executionStepParams
            );
            providerClient.processRequest(request);
            execution.setCurrentExecutionStepState(ExecutionStepState.IN_PROGRESS);
        } catch (Exception e) {
            execution.setCurrentExecutionStepState(ExecutionStepState.ERROR);
            e.printStackTrace();
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
