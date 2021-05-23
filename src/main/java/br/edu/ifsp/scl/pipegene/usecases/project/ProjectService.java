package br.edu.ifsp.scl.pipegene.usecases.project;

import br.edu.ifsp.scl.pipegene.domain.Project;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {

    Project createNewProject(String name, MultipartFile file);
}
