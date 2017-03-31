package com.adawan.api.rest.v1.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "metadata",
    "contentUrl"
})
public class FileResource extends  ResponseResource{
    private String id;
    @JsonProperty("metadata")
    private MetadataResource metadataResource;
    private String contentUrl;

    // Constructor
    public FileResource(){}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MetadataResource getMetadataResource() {
        return metadataResource;
    }

    public void setMetadataResource(MetadataResource metadataResource) {
        this.metadataResource = metadataResource;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
