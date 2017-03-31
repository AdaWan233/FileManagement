package com.adawan.api.rest.v1.mapper;

import com.adawan.api.rest.v1.domain.Metadata;
import com.adawan.api.rest.v1.resource.MetadataResource;
import org.springframework.stereotype.Component;

@Component
public class MetadataResourceMapper {
    /**
     * Map metadata entity to metadata resource
     * @param metadata Metadata entity
     * @return Mapped metadata resource
     */
    public MetadataResource mapEntityToMetadataResource(Metadata metadata){
        MetadataResource metadataResource = new MetadataResource();
        if(null != metadata){
            metadataResource.setFileName(metadata.getFileName());
            metadataResource.setAuthor(metadata.getAuthor());
            metadataResource.setType(metadata.getType());
            metadataResource.setSize(metadata.getSize());
        }
        return metadataResource;
    }
}
