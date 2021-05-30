package br.edu.ifsp.scl.pipegene.web.model.execution.response;

import java.util.UUID;

public class ExecutionResponse {

    private UUID executionId;

    public UUID getExecutionId() {
        return executionId;
    }

    public void setExecutionId(UUID executionId) {
        this.executionId = executionId;
    }

    public interface Builder {
        Builder executionId(UUID id);
        ExecutionResponse build();
    }

    private ExecutionResponse(BuilderImpl builder) {
        this.executionId = builder.executionId;
    }

    static final class BuilderImpl implements Builder {

        private UUID executionId;

        private BuilderImpl() { }


        @Override
        public Builder executionId(UUID id) {
            this.executionId = id;
            return this;
        }

        @Override
        public ExecutionResponse build() {
            return new ExecutionResponse(this);
        }
    }

    public static Builder builder() {
        return new BuilderImpl();
    }
}
