package br.edu.ifsp.scl.pipegene.external.storage.localstorage;

import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ObjectStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LocalStorageService implements ObjectStorageService {
    private static final String UPLOAD_PATH = "src/main/resources/static/uploads/";
    public static final Set<String> FILES = loadFiles();

    @Override
    public String putObject(MultipartFile multipartFile) {
        String filename = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
        File file = new File(UPLOAD_PATH + filename);
        System.out.println(UPLOAD_PATH + filename);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
            FILES.add(filename);
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(); // TODO(create a custom exception)
        }
    }

    private static Set<String> loadFiles() {
        try {
            return Stream.of(Objects.requireNonNull(new File(UPLOAD_PATH).listFiles()))
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(); // TODO(create a custom exception)
        }
    }
}
