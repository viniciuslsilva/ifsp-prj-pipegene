package br.edu.ifsp.scl.pipegene.web.model.execution.response;


import java.util.UUID;

public class ProjectExecutionStatusResponse {

    private UUID requestId;
    private String status;

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public interface Builder {
        Builder id(UUID id);
        Builder status(String id);
        ProjectExecutionStatusResponse build();
    }

    private ProjectExecutionStatusResponse(BuilderImpl builder) {
        this.requestId = builder.id;
        this.status = builder.status;
    }

    static final class BuilderImpl implements Builder {

        private UUID id;
        private String status;

        private BuilderImpl() { }

        @Override
        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder status(String id) {
            this.status = id;
            return this;
        }

        @Override
        public ProjectExecutionStatusResponse build() {
            return new ProjectExecutionStatusResponse(this);
        }
    }

    public static Builder builder() {
        return new BuilderImpl();
    }
}
