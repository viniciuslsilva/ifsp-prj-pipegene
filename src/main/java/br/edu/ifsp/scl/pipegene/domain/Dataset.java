package br.edu.ifsp.scl.pipegene.domain;

import java.util.UUID;

public class Dataset {
    private UUID id;
    private String filename;

    private Project project;

    public Dataset(UUID id, String filename, Project project) {
        this.id = id;
        this.filename = filename;
        this.project = project;
    }

    public static Dataset of(UUID id, String filename, Project project) {
        return new Dataset(id, filename, project);
    }

    public static Dataset createWithoutProject(UUID id, String filename) {
        return new Dataset(id, filename, null);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public UUID getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public Project getProject() {
        return project;
    }

    public UUID getProjectId() {
        return project.getId();
    }

    public String getFileType() {
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            return filename.substring(i+1);
        }
        return null;
    }
}
