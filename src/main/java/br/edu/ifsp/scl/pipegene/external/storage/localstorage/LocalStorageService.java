package br.edu.ifsp.scl.pipegene.external.storage.localstorage;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LocalStorageService implements ObjectStorageService {
    private final Logger logger = LoggerFactory.getLogger(LocalStorageService.class);

    private static final String ABSOLUTE_UPLOAD_PATH = "src/main/resources/static/uploads/";
    public static final Set<Dataset> FILES = loadFiles();

    @Override
    public Dataset putObject(MultipartFile multipartFile) {
        UUID id = UUID.randomUUID();
        String originalFilename = multipartFile.getOriginalFilename();
        String filename = id.toString() + "_uploads_" + originalFilename ;
        File file = new File(ABSOLUTE_UPLOAD_PATH + filename);

        writeFile(file, multipartFile::getBytes);

        Dataset dataset = Dataset.createWithoutProject(id, filename);
        FILES.add(dataset);

        return dataset;
    }

    @Override
    public File getObject(Dataset dataset) {
        logger.info(ABSOLUTE_UPLOAD_PATH + dataset.getFilename());
        if (FILES.stream().noneMatch(f -> f.getId().equals(dataset.getId()))) {
            logger.info("File not found:" + dataset.getFilename());
            return null;
        }

        return new File(ABSOLUTE_UPLOAD_PATH + dataset.getFilename());
    }


    private void writeFile(File file, Callable<byte[]> bytesFn) {
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(bytesFn.call());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(); // TODO(create a custom exception)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<Dataset> loadFiles() {
        try {
            return Stream.of(Objects.requireNonNull(new File(ABSOLUTE_UPLOAD_PATH).listFiles()))
                    .filter(file -> !file.isDirectory())
                    .map(file -> {
                        UUID id = UUID.fromString(file.getName().split("_uploads_")[0]);
                        return Dataset.createWithoutProject(id, file.getName());
                    })
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(); // TODO(create a custom exception)
        }
    }

}
