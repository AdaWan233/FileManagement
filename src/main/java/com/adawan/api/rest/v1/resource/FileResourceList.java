package com.adawan.api.rest.v1.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResourceList extends ResponseResource{
    @JsonProperty
    private List<FileResource> fileResourceList;

    // Constructor
    public FileResourceList(){}

    // Getter and setter
    public List<FileResource> getFileResourceList() {
        return fileResourceList;
    }

    public void setFileResourceList(List<FileResource> fileResourceList) {
        this.fileResourceList = fileResourceList;
    }

}
