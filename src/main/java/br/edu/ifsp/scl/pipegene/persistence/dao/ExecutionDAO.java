package br.edu.ifsp.scl.pipegene.persistence.dao;

import br.edu.ifsp.scl.pipegene.persistence.entities.ProjectEntity;
import br.edu.ifsp.scl.pipegene.persistence.entities.ExecutionStatusEntity;
import br.edu.ifsp.scl.pipegene.persistence.entities.ProviderEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ExecutionDAO {

    Boolean projectExists(UUID id);

    Optional<ProjectEntity> findProjectById(UUID id);

    Boolean bathProviderInfoIsValid(List<ProviderEntity> providers);

    Optional<ExecutionStatusEntity> findExecutionStatusByProjectIdAndExecutionId(UUID projectId, UUID executionId);

    void saveExecutionStatus(ExecutionStatusEntity executionStatusEntity);

}
