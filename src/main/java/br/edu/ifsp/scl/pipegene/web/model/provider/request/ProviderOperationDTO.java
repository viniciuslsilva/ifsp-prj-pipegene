package br.edu.ifsp.scl.pipegene.web.model.provider.request;

import br.edu.ifsp.scl.pipegene.domain.ProviderOperation;
import br.edu.ifsp.scl.pipegene.domain.ProviderOperationParam;

import java.util.List;
import java.util.stream.Collectors;

public class ProviderOperationDTO {
    private String type;
    private String description;
    private List<OperationParamDTO> params;

    public static ProviderOperationDTO createFromProviderOperation(ProviderOperation providerOperation) {
        return new ProviderOperationDTO(providerOperation.getType(), providerOperation.getDescription(),
                providerOperation.getParams().stream()
                        .map(OperationParamDTO::createFromProviderOperationParam)
                        .collect(Collectors.toList())
        );
    }

    public ProviderOperation convertToProviderOperation() {
        return new ProviderOperation(type, description, params.stream()
                .map(OperationParamDTO::toProviderOperation)
                .collect(Collectors.toList())
        );
    }

    public ProviderOperationDTO() {
    }

    public ProviderOperationDTO(String type, String description, List<OperationParamDTO> params) {
        this.type = type;
        this.description = description;
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public List<OperationParamDTO> getParams() {
        return params;
    }

    static class OperationParamDTO {
        private String type;
        private String option;
        private String key;
        private Object example;

        private static OperationParamDTO createFromProviderOperationParam(ProviderOperationParam param) {
            return new OperationParamDTO(param.getType(), param.getOption(), param.getKey(), param.getExample());
        }

        private ProviderOperationParam toProviderOperation() {
            return new ProviderOperationParam(type, option, key, example);
        }

        public OperationParamDTO() {
        }

        public OperationParamDTO(String type, String option, String key, Object example) {
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
}
