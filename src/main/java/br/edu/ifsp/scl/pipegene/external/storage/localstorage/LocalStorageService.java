package br.edu.ifsp.scl.pipegene.external.storage.localstorage;

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
    private static final String ABSOLUTE_TEMP_PATH = "src/main/resources/static/temp/";
    private static final String UPLOAD_PATH = "/static/uploads/";
    public static final Set<String> FILES = loadFiles();

    @Override
    public String putObject(MultipartFile multipartFile) {
        String filename = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
        File file = new File(ABSOLUTE_UPLOAD_PATH + filename);
        System.out.println(ABSOLUTE_UPLOAD_PATH + filename);

        writeFile(file, multipartFile::getBytes);
        FILES.add(filename);
        return filename;
    }

    @Override
    public File getObject(String filename) {
        logger.info(ABSOLUTE_UPLOAD_PATH +filename);
        if (!FILES.contains(filename)) {
            logger.info("File not found:" + filename);
            return null;
        }

        return new File(getClass().getResource(UPLOAD_PATH + filename).getFile());
    }

    @Override
    public String putTempObject(Resource file) {
        String filename = UUID.randomUUID().toString() + "_temp_" + file.getFilename();
        File tempFile = new File(ABSOLUTE_TEMP_PATH + filename);
        System.out.println(ABSOLUTE_TEMP_PATH + filename);

        writeFile(tempFile, () -> file.getInputStream().readAllBytes());
        return filename;
    }

    @Override
    public File getTempObject(String filename) {
        logger.info("getTempObject => "+ ABSOLUTE_TEMP_PATH +filename);
        return new File(getClass().getResource(ABSOLUTE_TEMP_PATH + filename).getFile());
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

    private static Set<String> loadFiles() {
        try {
            return Stream.of(Objects.requireNonNull(new File(ABSOLUTE_UPLOAD_PATH).listFiles()))
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(); // TODO(create a custom exception)
        }
    }

}
