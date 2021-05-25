package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ProviderRepositoryImpl implements ProviderRepository {

    private final FakeDatabase fakeDatabase;

    public ProviderRepositoryImpl(FakeDatabase fakeDatabase) {
        this.fakeDatabase = fakeDatabase;
    }

    @Override
    public Optional<Provider> findProviderById(UUID id) {
        if (fakeDatabase.PROVIDERS.containsKey(id)) {
            return Optional.of(fakeDatabase.PROVIDERS.get(id).toProvider());
        }

        return Optional.empty();
    }
}
