package br.edu.ifsp.scl.pipegene.domain;

import br.edu.ifsp.scl.pipegene.persistence.entities.ProjectEntity;

import java.util.UUID;

public class Project {

    private UUID id;
    private String datasetUrl;
    private String name;

    public static Project from(ProjectEntity projectEntity) {
        return new Project();
    }
}
