package br.edu.ifsp.scl.pipegene.usecases.project.gateway;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ObjectStorageService {

    Dataset putObject(MultipartFile file);

    File getObject(Dataset dataset);

}
