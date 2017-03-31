package com.adawan.api.rest.v1.mapper;

import com.adawan.api.rest.v1.domain.Metadata;
import com.adawan.api.rest.v1.resource.FileResource;
import com.adawan.api.rest.v1.resource.FileResourceList;
import com.adawan.api.rest.v1.resource.MetadataResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileResourceMapper {
    @Autowired
    private MetadataResourceMapper metadataResourceMapper;

    /**
     * Map metadata entity to file resource
     * @param metadata Metadata entity
     * @param request HttpServletRequest
     * @return Mapped file resource
     */
    public FileResource mapEntityToFileResource(Metadata metadata, HttpServletRequest request){
        FileResource fileResource = new FileResource();
        if(null != metadata){
            MetadataResource metadataResource = metadataResourceMapper.mapEntityToMetadataResource(metadata);
            fileResource.setMetadataResource(metadataResource);
            fileResource.setId(metadata.getId());
            fileResource.setContentUrl(request.getRequestURL().toString()+"/"+metadata.getId()+"/content");
        }
        return fileResource;
    }

    /**
     * Map metadata entities to file resource only for file id and content URL
     * @param metadataList List of metadata entities
     * @param request HttpServletRequest
     * @return Mapped file resource list
     */
    public FileResourceList mapEntitiesToFileResourceWithoutMetadata(List<Metadata> metadataList, HttpServletRequest request){
        FileResourceList fileResourceList = new FileResourceList();
        List<FileResource> fileResources = new ArrayList<FileResource>();

        if(null != metadataList){
            for(Metadata metadata:metadataList){
                if(null != metadata){
                    FileResource fileResource = new FileResource();
                    // Only map file id and content url
                    fileResource.setId(metadata.getId());
                    fileResource.setContentUrl(request.getRequestURL().toString()+"/"+metadata.getId()+"/content");
                    fileResources.add(fileResource);
                }
            }
        }

        fileResourceList.setFileResourceList(fileResources);
        return fileResourceList;
    }

    /**
     * Map metadata entities to file resource
     * @param metadataList List of metadata entities
     * @param request HttpServletRequest
     * @return Mapped file resource list
     */
    public FileResourceList mapEntitiesToFileResource(List<Metadata> metadataList, HttpServletRequest request){
        FileResourceList fileResourceList = new FileResourceList();
        List<FileResource> fileResources = new ArrayList<FileResource>();

        if(null != metadataList){
            for(Metadata metadata:metadataList){
                if(null != metadata){
                    FileResource fileResource = new FileResource();
                    MetadataResource metadataResource = metadataResourceMapper.mapEntityToMetadataResource(metadata);
                    fileResource.setMetadataResource(metadataResource);
                    fileResource.setId(metadata.getId());
                    fileResource.setContentUrl(request.getRequestURL().toString()+"/"+metadata.getId()+"/content");
                    fileResources.add(fileResource);
                }
            }
        }

        fileResourceList.setFileResourceList(fileResources);
        return fileResourceList;
    }

}
