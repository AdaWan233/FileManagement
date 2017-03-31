package com.adawan.api.rest.v1.controller;

import com.adawan.api.rest.v1.domain.Metadata;
import com.adawan.api.rest.v1.mapper.FileResourceMapper;
import com.adawan.api.rest.v1.mapper.MetadataResourceMapper;
import com.adawan.api.rest.v1.resource.ErrorResource;
import com.adawan.api.rest.v1.resource.ResponseResource;
import com.adawan.api.rest.v1.service.FileService;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("rest/v1/files")
public class FileController {

    private static final Logger LOG = Logger.getLogger(FileController.class);
    private static final String className = FileController.class.getSimpleName();
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String NOT_FOUND = "Resource not found";

    @Autowired
    private FileService fileService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FileResourceMapper fileResourceMapper;

    @Autowired
    private MetadataResourceMapper metadataResourceMapper;

    /**
     * Upload a file with a few metadata fields
     * @param file File uploaded
     * @param author Author of the file
     * @return FileResource
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<? extends ResponseResource> uploadFile(@RequestParam MultipartFile file, @RequestParam String author) {
        try {
            LOG.info(className + "::uploadFile::Request received.");

            // Save file and related metadata
            Metadata metadata = fileService.saveFileAndMetadata(file, author);

            LOG.info(className + "::uploadFile::Uploaded successfully.");

            // Map and return response
            return new ResponseEntity(fileResourceMapper.mapEntityToFileResource(metadata, request), HttpStatus.CREATED);
        } catch (Exception e) {
            LOG.error(className + "::uploadFile::HttpStatusCode 500. Error: " + e.getMessage());
            return new ResponseEntity(new ErrorResource(INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get metadata by file id
     * @param fileId File id
     * @return File Resource
     */
    @RequestMapping(value = "/{fileId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<? extends ResponseResource> getMetadataByFileId(@PathVariable String fileId){
        try{
            LOG.info(className + "::getMetadataByFileId::Request received.");

            // Find file metadata by fileId
            Metadata metadata = fileService.getMetadataByFileId(fileId);

            if(null == metadata){
                String cause = "You may have an invalid fileId.";
                String resolution = "Please try with valid fileId.";
                LOG.info(className + "::getMetadataByFileId::Related metadata not found.");
                return new ResponseEntity(new ErrorResource(NOT_FOUND, cause, resolution), HttpStatus.NOT_FOUND);
            }

            LOG.info(className + "::getMetadataByFileId::Response initialized.");

            // Map and return response
            return new ResponseEntity(metadataResourceMapper.mapEntityToMetadataResource(metadata), HttpStatus.OK);
        }catch (Exception e){
            LOG.error(className + "::getMetadataByFileId::HttpStatusCode 500. Error: " + e.getMessage());
            return new ResponseEntity(new ErrorResource(INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get file ids by author
     * @param author File author
     * @return List of file resources
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<? extends ResponseResource> getFileIdsByAuthor(@RequestParam(value = "field", required = false) String field,
                                                                                       @RequestParam(value = "author") String author){
        try{
            LOG.info(className + "::getFileIdsByAuthor::Request received.");

            // Find file metadatas by author
            List<Metadata> metadatas = fileService.getMetadatasByAuthor(author);

            LOG.info(className + "::getFileIdsByAuthor::Response initialized.");

            // Map and return response
            if(null != field && field.equals("id")){
                // Only return file id and content url
                return new ResponseEntity(fileResourceMapper.mapEntitiesToFileResourceWithoutMetadata(metadatas, request), HttpStatus.OK);
            }else{
                // Return file id, metadata and content url
                return new ResponseEntity(fileResourceMapper.mapEntitiesToFileResource(metadatas, request), HttpStatus.OK);
            }
        }catch (Exception e){
            LOG.error(className + "::getFileIdsByAuthor::HttpStatusCode 500. Error: " + e.getMessage());
            return new ResponseEntity(new ErrorResource(INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get file content by file id
     * @param fileId File id
     * @param response HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "/{fileId}/content", method = RequestMethod.GET)
    public @ResponseBody void getFileContent(@PathVariable(value = "fileId") String fileId, HttpServletResponse response) throws IOException {
        try{
            LOG.info(className + "::getFileContent::Request received.");

            // Find file metadata by fileId
            Metadata metadata = fileService.getMetadataByFileId(fileId);

            if(null == metadata){
                String cause = "You may have an invalid fileId.";
                String resolution = "Please try with valid fileId.";
                LOG.info(className + "::getFileContent::Related metadata not found.");
                response.sendError(404, NOT_FOUND);
            }

            // Get file path
            String filePath = System.getProperty("user.dir")+"/uploads/"+fileId+"/"+metadata.getFileName();

            // Get file stream
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);

            // Set the content type and attachment header
            response.addHeader("Content-disposition", "attachment;filename="+metadata.getFileName());
            response.setContentType("application/octet-stream");

            // Copy the stream to the response's output stream
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();

        }catch (Exception e){
            LOG.error(className + "::getFileContent::HttpStatusCode 500. Error: " + e.getMessage());
            response.sendError(500, INTERNAL_SERVER_ERROR);
        }
    }
}
