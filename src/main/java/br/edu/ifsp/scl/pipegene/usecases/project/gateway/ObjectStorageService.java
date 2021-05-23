package br.edu.ifsp.scl.pipegene.usecases.project.gateway;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorageService {

    String putObject(MultipartFile file);
}
