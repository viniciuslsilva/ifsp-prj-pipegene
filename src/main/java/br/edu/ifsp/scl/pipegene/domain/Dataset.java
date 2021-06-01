package br.edu.ifsp.scl.pipegene.domain;

import java.util.UUID;

public class Dataset {
    private UUID id;
    private String filename;


    public static Dataset createWithoutFilename(UUID id) {
        return new Dataset(id, null);
    }

    public Dataset(UUID id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public static Dataset of(UUID id, String filename) {
        return new Dataset(id, filename);
    }

    public UUID getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }
}
