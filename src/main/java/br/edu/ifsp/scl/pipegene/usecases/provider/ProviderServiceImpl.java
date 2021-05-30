package br.edu.ifsp.scl.pipegene.usecases.provider;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderRepository;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public List<Provider> listAllProviders() {
        return providerRepository.findAllProviders();
    }

    @Override
    public Provider createNewProject(ProviderRequest providerRequest) {
        return providerRepository.saveNewProvider(providerRequest.toProvider());
    }
}
