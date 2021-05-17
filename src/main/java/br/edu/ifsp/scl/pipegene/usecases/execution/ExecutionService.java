package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.persistence.dao.ExecutionDAO;
import br.edu.ifsp.scl.pipegene.persistence.entities.ExecutionStatusEntity;
import br.edu.ifsp.scl.pipegene.persistence.entities.ExecutionStatusEnum;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.QueueService;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExecutionService {

    private final ExecutionDAO executionDAO;
    private final QueueService queueService;

    public ExecutionService(ExecutionDAO executionDAO, QueueService queueService) {
        this.executionDAO = executionDAO;
        this.queueService = queueService;
    }

    public UUID addNewExecution(UUID projectId, ExecutionRequest executionRequest) {
        Boolean projectExists = executionDAO.projectExists(projectId);
        if (!projectExists) {
            throw new IllegalArgumentException(); // TODO(create a custom exception)
        }

        Boolean executionRequestIsValid = executionDAO.bathProviderInfoIsValid(
                executionRequest.getExecutionRequestFlowDetails().stream()
                        .map(Provider::from)
                        .map(Provider::toProviderEntity)
                        .collect(Collectors.toList())
        );

        if (!executionRequestIsValid) {
            throw new IllegalArgumentException(); // TODO(create a custom exception)
        }

        UUID processId = queueService.add(executionRequest);
        executionDAO.saveExecutionStatus(
                new ExecutionStatusEntity(processId, projectId, ExecutionStatusEnum.WAITING)
        );

        return processId;
    }

    public String checkProjectExecutionStatus(UUID projectId, UUID executionId) {
        Boolean projectExists = executionDAO.projectExists(projectId);
        if (!projectExists) {
            throw new IllegalArgumentException(); // TODO(create a custom exception)
        }

        Optional<ExecutionStatusEntity> opt = executionDAO.findExecutionStatusByProjectIdAndExecutionId(projectId, executionId);

        if (opt.isEmpty()) {
            throw new IllegalArgumentException(); // TODO(create a custom exception)
        }

        return opt.get().getStatus().name();
    }
}
