package com.adawan.api.rest.v1.domain;


import javax.persistence.*;

@Entity
@Table(name = "metadata")
public class Metadata {
    @Id
    private String id;
    @Column
    private String author;
    @Column(name = "file_name")
    private String fileName;
    @Column
    private Long size;
    @Column
    private String type;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

}
