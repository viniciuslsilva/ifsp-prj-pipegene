package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ProviderEntity;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProviderDAOImpl implements ProviderDAO {

    private final FakeDatabase fakeDatabase;

    public ProviderDAOImpl(FakeDatabase fakeDatabase) {
        this.fakeDatabase = fakeDatabase;
    }

    @Override
    public Optional<Provider> findProviderById(UUID id) {
        if (fakeDatabase.PROVIDERS.containsKey(id)) {
            return Optional.of(fakeDatabase.PROVIDERS.get(id).convertToProvider());
        }

        return Optional.empty();
    }

    @Override
    public List<Provider> findAllProviders() {
        return fakeDatabase.PROVIDERS.values().stream()
                .map(ProviderEntity::convertToProvider)
                .collect(Collectors.toList());
    }

    @Override
    public Provider saveNewProvider(Provider provider) {
        ProviderEntity entity = ProviderEntity.createFromProviderWithoutId(provider);
        fakeDatabase.PROVIDERS.put(entity.getId(), entity);
        return entity.convertToProvider();
    }
}
