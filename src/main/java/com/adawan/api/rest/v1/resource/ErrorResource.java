package com.adawan.api.rest.v1.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
    "error",
    "cause",
    "resolution"
})
public class ErrorResource extends ResponseResource{
    private String error;
    private String cause;
    private String resolution;

    // Constructors
    public ErrorResource(String error, String cause){
        this.error = error;
        this.cause = cause;
    }

    public ErrorResource(String error, String cause, String resolution){
        this.error = error;
        this.cause = cause;
        this.resolution = resolution;
    }

    // Getters and setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
