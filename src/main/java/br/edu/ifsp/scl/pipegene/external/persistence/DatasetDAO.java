package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Dataset;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface DatasetDAO {

    List<Dataset> findDatasetsByProjectIds(Collection<UUID> projectIds);

    List<Dataset> findDatasetsByProjectId(UUID projectId);
}
