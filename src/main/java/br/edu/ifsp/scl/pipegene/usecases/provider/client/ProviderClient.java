package br.edu.ifsp.scl.pipegene.usecases.provider.client;

import br.edu.ifsp.scl.pipegene.usecases.provider.model.ProviderResponse;
import org.springframework.core.io.Resource;

import java.io.File;
import java.net.URI;
import java.util.UUID;

public interface ProviderClient {

    ProviderResponse postFile(UUID operationId, URI uri, File file);

    Resource getFile(URI uri);
}
