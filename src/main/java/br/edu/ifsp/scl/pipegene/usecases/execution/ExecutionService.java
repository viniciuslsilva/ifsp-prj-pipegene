package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;

import java.util.UUID;

public interface ExecutionService {

    UUID addNewExecution(UUID projectId, ExecutionRequest executionRequest);

    Execution findExecutionById(UUID projectId, UUID executionId);

}
