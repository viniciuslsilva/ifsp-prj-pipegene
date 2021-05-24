package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.domain.ExecutionStatus;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStatusEnum;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionRepository;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.QueueService;
import br.edu.ifsp.scl.pipegene.web.exception.GenericResourceException;
import br.edu.ifsp.scl.pipegene.web.exception.ResourceNotFoundException;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    private final ExecutionRepository executionRepository;
    private final QueueService queueService;

    public ExecutionServiceImpl(ExecutionRepository executionRepository, QueueService queueService) {
        this.executionRepository = executionRepository;
        this.queueService = queueService;
    }

    @Override
    public UUID addNewExecution(UUID projectId, ExecutionRequest executionRequest) {
        Boolean projectExists = executionRepository.projectExists(projectId);
        if (!projectExists) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        Boolean executionRequestIsValid = executionRepository.bathProviderInfoIsValid(
                executionRequest.getExecutionRequestFlowDetails().stream()
                        .map(Provider::fromExecutionRequestFlowDetails)
                        .collect(Collectors.toList())
        );

        if (!executionRequestIsValid) {
            throw new GenericResourceException("Please, verify provider id, inputType and outputType", "Invalid Execution Request");
        }

        UUID processId = queueService.add(executionRequest);
        executionRepository.saveExecutionStatus(
                ExecutionStatus.of(processId, projectId, ExecutionStatusEnum.WAITING)
        );

        return processId;
    }

    @Override
    public String checkProjectExecutionStatus(UUID projectId, UUID executionId) {
        Boolean projectExists = executionRepository.projectExists(projectId);
        if (!projectExists) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        Optional<ExecutionStatus> opt = executionRepository.findExecutionStatusByProjectIdAndExecutionId(projectId, executionId);

        if (opt.isEmpty()) {
            throw new ResourceNotFoundException("Not found execution with id: " + executionId);
        }

        return opt.get().getStatus().name();
    }
}
