package br.edu.ifsp.scl.pipegene.web.model.execution.response;

import java.util.UUID;

public class ExecutionResponse {

    private UUID request_id;
    private UUID processId;

    public UUID getRequest_id() {
        return request_id;
    }

    public void setRequest_id(UUID request_id) {
        this.request_id = request_id;
    }

    public UUID getProcessId() {
        return processId;
    }

    public void setProcessId(UUID processId) {
        this.processId = processId;
    }

    public interface Builder {
        Builder id(UUID id);
        Builder processId(UUID id);
        ExecutionResponse build();
    }

    private ExecutionResponse(BuilderImpl builder) {
        this.request_id = builder.id;
        this.processId = builder.processId;
    }

    static final class BuilderImpl implements Builder {

        private UUID id;
        private UUID processId;

        private BuilderImpl() { }

        @Override
        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder processId(UUID id) {
            this.processId = id;
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
