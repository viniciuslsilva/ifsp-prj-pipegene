package br.edu.ifsp.scl.pipegene.usecases.provider.client;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.UUID;

@Component
public class ProviderClientImpl implements ProviderClient {

    private final Logger logger = LoggerFactory.getLogger(ProviderClientImpl.class);

    @Qualifier("ProviderHttpClient")
    private final RestTemplate restTemplate;

    private final ObjectStorageService storageService;

    public ProviderClientImpl(RestTemplate restTemplate, ObjectStorageService storageService) {
        this.restTemplate = restTemplate;
        this.storageService = storageService;
    }

    @Override
    public void postFile(UUID operationId, Provider provider, String datasetUrl) {
        File file = storageService.getObject(datasetUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("x-pipegene-operation-id", operationId.toString());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            logger.info(provider.getURI().toString());
            logger.info(request.toString());
            String r = restTemplate.postForObject(provider.getURI(), request, String.class);
            logger.info(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
