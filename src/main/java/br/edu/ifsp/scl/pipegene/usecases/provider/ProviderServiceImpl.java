package br.edu.ifsp.scl.pipegene.usecases.provider;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderDAO;
import br.edu.ifsp.scl.pipegene.web.model.provider.request.ProviderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderDAO providerDAO;

    public ProviderServiceImpl(ProviderDAO providerDAO) {
        this.providerDAO = providerDAO;
    }

    @Override
    public List<Provider> listAllProviders() {
        return providerDAO.findAllProviders();
    }

    @Override
    public Provider createNewProvider(ProviderRequest providerRequest) {
        return providerDAO.saveNewProvider(providerRequest.convertToProvider());
    }
}
