package br.edu.ifsp.scl.pipegene.external.persistence.entities;

import br.edu.ifsp.scl.pipegene.domain.Dataset;

import java.util.UUID;

public class DatasetEntity {
    private UUID id;
    private String filename;


    public static DatasetEntity createFromDataset(Dataset d) {
        return new DatasetEntity(d.getId(), d.getFilename());
    }

    public Dataset convertToDataset() {
        return new Dataset(id, filename, null);
    }

    public DatasetEntity() {
    }

    public DatasetEntity(UUID id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public UUID getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }
}
