package br.edu.ifsp.scl.pipegene.external.client;

import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderClient;
import br.edu.ifsp.scl.pipegene.external.client.model.ProviderResponse;
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

import java.io.File;
import java.net.URI;
import java.util.UUID;

@Component
public class ProviderClientImpl implements ProviderClient {

    private final Logger logger = LoggerFactory.getLogger(ProviderClientImpl.class);

    @Qualifier("ProviderHttpClient")
    private final RestTemplate restTemplate;

    public ProviderClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProviderResponse processRequest(UUID executionId, UUID stepId, String url, File file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("x-pipegene-execution-id", executionId.toString());
        headers.add("x-pipegene-step-id", stepId.toString());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            logger.info("POST request for " + request.toString());
            URI uri = URI.create(url + "/v1/pipegine/provider/process");
            ProviderResponse r = restTemplate.postForObject(uri, request, ProviderResponse.class);
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
