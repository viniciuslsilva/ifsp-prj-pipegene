package br.edu.ifsp.scl.pipegene.web.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDetails {
    @JsonProperty
    protected String title;
    @JsonProperty
    protected int status;
    @JsonProperty
    protected String detail;
    @JsonProperty
    protected long timestamp;
    @JsonProperty
    protected String developerMessage;

    public static final class Builder {
        protected String title;
        protected int status;
        protected String detail;
        protected long timestamp;
        protected String developerMessage;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ErrorDetails build() {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.timestamp = this.timestamp;
            errorDetails.developerMessage = this.developerMessage;
            errorDetails.title = this.title;
            errorDetails.status = this.status;
            errorDetails.detail = this.detail;
            return errorDetails;
        }
    }
}