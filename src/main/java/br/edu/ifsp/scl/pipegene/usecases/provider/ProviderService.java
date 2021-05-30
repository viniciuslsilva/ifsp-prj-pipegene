package br.edu.ifsp.scl.pipegene.usecases.provider;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.web.model.provider.ProviderRequest;

import java.util.List;

public interface ProviderService {

    List<Provider> listAllProviders();

    Provider createNewProject(ProviderRequest providerRequest);
}
