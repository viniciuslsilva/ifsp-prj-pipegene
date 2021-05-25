package br.edu.ifsp.scl.pipegene.usecases.project.gateway;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ObjectStorageService {

    String putObject(MultipartFile file);

    File getObject(String location);
}
