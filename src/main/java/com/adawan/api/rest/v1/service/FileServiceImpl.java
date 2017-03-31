package com.adawan.api.rest.v1.service;

import com.adawan.api.rest.v1.controller.FileController;
import com.adawan.api.rest.v1.domain.Metadata;
import com.adawan.api.rest.v1.domain.MetadataRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    private static final Logger LOG = Logger.getLogger(FileController.class);
    private static final String className = FileController.class.getSimpleName();
    private static final String UPLOAD_DIR = "/uploads/";

    @Autowired
    private MetadataRepository metadataRepository;

    /**
     * Save file content on file system and metadata in Database
     * @param file Uploaded file
     * @param author File metaData
     */
    @Transactional
    public Metadata saveFileAndMetadata(MultipartFile file, String author) throws IOException {
        LOG.info(className + "::saveFileAndMetadata::Saving file content and metadata.");
        // Generate file id
        String fileId = UUID.randomUUID().toString();

        // Get root path
        String rootPath = System.getProperty("user.dir");

        // Save file content on the file system
        File uploadDir = new File(rootPath+UPLOAD_DIR+fileId+"/");
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        File dest = new File(uploadDir.getAbsolutePath()+"/"+file.getOriginalFilename());
        file.transferTo(dest);

        // Construct metadata entity
        Metadata metadata = new Metadata();
        metadata.setType(file.getContentType());
        metadata.setSize(file.getSize());
        metadata.setAuthor(author);
        metadata.setFileName(file.getOriginalFilename());
        metadata.setId(fileId);

        // Save metadata in Database
        metadataRepository.saveAndFlush(metadata);

        LOG.info(className + "::saveFileAndMetadata::Saved");
        return metadata;
    }

    /**
     * Get metadata by file id
     * @param fileId File id
     * @return Single metadata
     */
    @Transactional
    public Metadata getMetadataByFileId(String fileId){
        LOG.info(className + "::getMetadataByFileId::Searching metadata by fild id.");
        return metadataRepository.getMetaDataById(fileId);
    }

    /**
     * Get metadata by author
     * @param author File author
     * @return List of metadata
     */
    @Transactional
    public List<Metadata> getMetadatasByAuthor(String author){
        LOG.info(className + "::getMetadatasByAuthor::Searching metadatas by author.");
        return metadataRepository.getMetaDatasByAuthor(author);
    }

}
