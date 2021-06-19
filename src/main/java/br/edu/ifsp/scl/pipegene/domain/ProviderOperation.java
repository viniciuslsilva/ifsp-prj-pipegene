package br.edu.ifsp.scl.pipegene.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProviderOperation {
    private String type;
    private String description;
    private List<ProviderOperationParam> params;

    public ProviderOperation() {
    }

    public ProviderOperation(String type, String description, Collection<ProviderOperationParam> params) {
        this.type = type;
        this.description = description;
        this.params = new ArrayList<>(params);
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public List<ProviderOperationParam> getParams() {
        return new ArrayList<>(params);
    }
}
