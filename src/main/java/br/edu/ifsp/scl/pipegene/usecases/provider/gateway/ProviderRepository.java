package br.edu.ifsp.scl.pipegene.usecases.provider.gateway;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.Optional;
import java.util.UUID;


public interface ProviderRepository {

    Optional<Provider> findProviderById(UUID id);
}
