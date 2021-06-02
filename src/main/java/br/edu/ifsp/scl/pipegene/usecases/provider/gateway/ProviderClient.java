package br.edu.ifsp.scl.pipegene.usecases.provider.gateway;

import br.edu.ifsp.scl.pipegene.external.client.model.ProviderClientRequest;
import br.edu.ifsp.scl.pipegene.external.client.model.ProviderResponse;
import org.springframework.core.io.Resource;

import java.io.File;
import java.net.URI;
import java.util.UUID;

public interface ProviderClient {

    ProviderResponse processRequest(ProviderClientRequest request);

    Resource retrieveProcessedFileRequest(URI uri);
}
