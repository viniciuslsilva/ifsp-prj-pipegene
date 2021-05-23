package br.edu.ifsp.scl.pipegene.web.model.execution.response;

import java.util.UUID;

public class ExecutionResponse {

    private UUID requestId;
    private UUID processId;

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public UUID getProcessId() {
        return processId;
    }

    public void setProcessId(UUID processId) {
        this.processId = processId;
    }

    public interface Builder {
        Builder requestId(UUID id);
        Builder processId(UUID id);
        ExecutionResponse build();
    }

    private ExecutionResponse(BuilderImpl builder) {
        this.requestId = builder.requestId;
        this.processId = builder.processId;
    }

    static final class BuilderImpl implements Builder {

        private UUID requestId;
        private UUID processId;

        private BuilderImpl() { }

        @Override
        public Builder requestId(UUID id) {
            this.requestId = id;
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
