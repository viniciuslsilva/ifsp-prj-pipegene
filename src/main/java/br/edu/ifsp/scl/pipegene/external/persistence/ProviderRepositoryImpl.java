package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ProviderEntity;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<Provider> findAllProviders() {
        return fakeDatabase.PROVIDERS.values().stream()
                .map(ProviderEntity::toProvider)
                .collect(Collectors.toList());
    }

    @Override
    public Provider saveNewProvider(Provider provider) {
        UUID id = UUID.randomUUID();
        provider.setId(id);
        fakeDatabase.PROVIDERS.put(id, ProviderEntity.of(provider));
        return provider;
    }
}
