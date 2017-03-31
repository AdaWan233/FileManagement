package com.adawan.api.rest.v1.service;

import com.adawan.api.rest.v1.domain.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    Metadata saveFileAndMetadata(MultipartFile file, String author) throws IOException;
    Metadata getMetadataByFileId(String fileId);
    List<Metadata> getMetadatasByAuthor(String author);
}
