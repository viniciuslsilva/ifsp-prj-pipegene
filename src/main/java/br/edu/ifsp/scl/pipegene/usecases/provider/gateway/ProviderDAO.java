package br.edu.ifsp.scl.pipegene.usecases.provider.gateway;

import br.edu.ifsp.scl.pipegene.domain.Provider;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProviderDAO {

    Optional<Provider> findProviderById(UUID id);

    List<Provider> findAllProviders();

    Provider saveNewProvider(Provider provider);
}
