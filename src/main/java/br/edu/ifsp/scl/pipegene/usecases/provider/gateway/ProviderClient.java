package br.edu.ifsp.scl.pipegene.usecases.provider.gateway;

import br.edu.ifsp.scl.pipegene.external.client.model.ProviderClientRequest;
import br.edu.ifsp.scl.pipegene.external.client.model.ProviderResponse;
import org.springframework.core.io.Resource;

import java.net.URI;

public interface ProviderClient {

    ProviderResponse processRequest(ProviderClientRequest request);

    Resource retrieveProcessedFileRequest(URI uri);
}
