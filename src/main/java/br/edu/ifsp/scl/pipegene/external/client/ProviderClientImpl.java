package br.edu.ifsp.scl.pipegene.external.client;

import br.edu.ifsp.scl.pipegene.domain.Operation;
import br.edu.ifsp.scl.pipegene.external.client.model.ProviderClientRequest;
import br.edu.ifsp.scl.pipegene.external.client.model.ProviderResponse;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class ProviderClientImpl implements ProviderClient {

    private final Logger logger = LoggerFactory.getLogger(ProviderClientImpl.class);

    @Qualifier("ProviderHttpClient")
    private final RestTemplate restTemplate;

    public ProviderClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProviderResponse processRequest(ProviderClientRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("x-pipegene-execution-id", request.getExecutionId().toString());
        headers.add("x-pipegene-step-id", request.getStepId().toString());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(request.getFile()));

        Operation operation = request.getOperation();
        body.add(operation.getParamKey(), operation.getParams());

        HttpEntity<MultiValueMap<String, Object>> req = new HttpEntity<>(body, headers);

        try {
            logger.info("POST request for " + req.toString());
            URI uri = URI.create(request.getUrl() + "/v1/pipegine/provider/process");
            ProviderResponse r = restTemplate.postForObject(uri, req, ProviderResponse.class);
            logger.info("Response: " + r);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Resource retrieveProcessedFileRequest(URI uri) {
        return restTemplate.getForObject(uri, ByteArrayResource.class);
    }
}
