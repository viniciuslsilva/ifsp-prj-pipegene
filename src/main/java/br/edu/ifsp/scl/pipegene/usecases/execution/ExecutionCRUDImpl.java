package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStatusEnum;
import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionDAO;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.QueueService;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectDAO;
import br.edu.ifsp.scl.pipegene.web.exception.GenericResourceException;
import br.edu.ifsp.scl.pipegene.web.exception.ResourceNotFoundException;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionStepRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExecutionCRUDImpl implements ExecutionCRUD {

    private final ExecutionDAO executionDAO;
    private final ProjectDAO projectDAO;
    private final QueueService queueService;

    public ExecutionCRUDImpl(ExecutionDAO executionDAO, ProjectDAO projectDAO, QueueService queueService) {
        this.executionDAO = executionDAO;
        this.projectDAO = projectDAO;
        this.queueService = queueService;
    }

    @Override
    public Execution addNewExecution(UUID projectId, ExecutionRequest executionRequest) {
        Optional<Project> optionalProject = projectDAO.findProjectById(projectId);
        if (optionalProject.isEmpty()) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }
        Project project = optionalProject.get();

        if (!project.hasDataset(executionRequest.getDataset())) {
            throw new ResourceNotFoundException("Not found dataset: " + executionRequest.getDataset());
        }

        Boolean executionRequestIsValid = executionDAO.bathProviderInfoIsValid(
                executionRequest.getExecutionSteps().stream()
                        .map(ExecutionStepRequest::convertToProvider)
                        .collect(Collectors.toList())
        );

        if (!executionRequestIsValid) {
            throw new GenericResourceException("Please, verify provider id, inputType and outputType", "Invalid Execution Request");
        }

        UUID executionId = queueService.add(executionRequest);
        Dataset dataset = project.findDatasetById(executionRequest.getDataset());

        return executionDAO.saveExecution(
                Execution.createWithPartialValues(executionId, project, dataset, ExecutionStatusEnum.WAITING)
        );
    }

    @Override
    public Execution findExecutionById(UUID projectId, UUID executionId) {
        Boolean projectExists = projectDAO.projectExists(projectId);
        if (!projectExists) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        Optional<Execution> opt = executionDAO.findExecutionByProjectIdAndExecutionId(projectId, executionId);

        if (opt.isEmpty()) {
            throw new ResourceNotFoundException("Not found execution with id: " + executionId);
        }

        return opt.get();
    }

    @Override
    public List<Execution> listAllExecutions(UUID projectId) {
        Boolean projectExists = projectDAO.projectExists(projectId);
        if (!projectExists) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        return projectDAO.findAllExecutionsByProjectId(projectId);
    }
}
