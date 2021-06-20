package br.edu.ifsp.scl.pipegene.domain;

public class ProviderOperationParam {

    private String type;
    private String option;
    private String key;
    private String name;
    private Object example;

    public ProviderOperationParam() {
    }

    public ProviderOperationParam(String type, String option, String key, String name, Object example) {
        this.type = type;
        this.option = option;
        this.key = key;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public Object getExample() {
        return example;
    }
}
