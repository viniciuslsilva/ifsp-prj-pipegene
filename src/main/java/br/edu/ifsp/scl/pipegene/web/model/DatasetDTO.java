package br.edu.ifsp.scl.pipegene.web.model;

import br.edu.ifsp.scl.pipegene.domain.Dataset;

import java.util.UUID;

public class DatasetDTO {

    private UUID id;
    private String filename;

    public static DatasetDTO createFromDataset(Dataset dataset) {
        return new DatasetDTO(dataset.getId(), dataset.getFilename());
    }

    private DatasetDTO() { }

    private DatasetDTO(UUID id, String filename) {
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
