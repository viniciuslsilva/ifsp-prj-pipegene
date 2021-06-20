package br.edu.ifsp.scl.pipegene.usecases.execution;

import br.edu.ifsp.scl.pipegene.domain.Execution;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.CreateExecutionRequest;

import java.util.List;
import java.util.UUID;

public interface ExecutionCRUD {

    Execution addNewExecution(UUID projectId, CreateExecutionRequest request);

    Execution findExecutionById(UUID projectId, UUID executionId);

    List<Execution> listAllExecutionsByProjectId(UUID projectId);

    List<Execution> listAllExecutionByUserId(UUID userId);
}
