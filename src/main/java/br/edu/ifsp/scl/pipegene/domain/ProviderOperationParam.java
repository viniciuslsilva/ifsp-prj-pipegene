package br.edu.ifsp.scl.pipegene.domain;

public class ProviderOperationParam {

    private String type;
    private String option;
    private String key;
    private Object example;

    public ProviderOperationParam(String type, String option, String key, Object example) {
        this.type = type;
        this.option = option;
        this.key = key;
        this.example = example;
    }

    public String getType() {
        return type;
    }

    public String getOption() {
        return option;
    }

    public String getKey() {
        return key;
    }

    public Object getExample() {
        return example;
    }
}
