package br.edu.ifsp.scl.pipegene.usecases.provider.client;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.UUID;

public interface ProviderClient {

    void postFile(UUID operationId, Provider provider, String datasetUrl);
}
