package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;

import java.util.UUID;

public interface ExecutionService {

    UUID addNewExecution(UUID projectId, ExecutionRequest executionRequest);

    String checkProjectExecutionStatus(UUID projectId, UUID executionId);

}
