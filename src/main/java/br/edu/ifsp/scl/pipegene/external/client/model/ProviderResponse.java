package br.edu.ifsp.scl.pipegene.external.client.model;

public class ProviderResponse {
    private String urlToCheck;
    private String message;

    public String getUrlToCheck() {
        return urlToCheck;
    }

    public void setUrlToCheck(String urlToCheck) {
        this.urlToCheck = urlToCheck;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ProviderResponse{" +
                "urlToCheck='" + urlToCheck + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
