package com.fadgiras.exos.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Access;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fadgiras.exos.enums.ErrorCodeEnum;
import com.fadgiras.exos.exception.StorageException;
import com.fadgiras.exos.model.DBFile;
import com.fadgiras.exos.repository.FilesRepository;
import com.fadgiras.exos.service.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private Resource resource;

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ObjectMapper objectMapper;


    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestParam MultipartFile file) throws StorageException, JsonProcessingException {

        storageService.uploadFile(file);

        return objectMapper.writeValueAsString("ok");
    }


    @RequestMapping(value = "/fileDelete/{uuid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String delete(@PathVariable(value="uuid") String uuid) throws StorageException, JsonProcessingException{
        DBFile file;
        try {
            Optional<DBFile> optionalEntity =  filesRepository.findFileByUUID(uuid);
            file = optionalEntity.get();        
        } catch (Exception e) {
            throw new StorageException(ErrorCodeEnum.SE001E.name());
        }

        storageService.deleteFile(file.getId());

        return objectMapper.writeValueAsString("ok");
    }


    @RequestMapping(value = "/fileDownload/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<?> download(@PathVariable String uuid, HttpServletRequest request) throws MalformedURLException, StorageException {

        //Loading file
        DBFile file;        
        try {
            Optional<DBFile> optionalEntity =  filesRepository.findFileByUUID(uuid);
            file = optionalEntity.get();        
        } catch (Exception e) {
            throw new StorageException(ErrorCodeEnum.SE001D.name());
        }

        try {
            resource  = storageService.downloadFile(file.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new StorageException(ErrorCodeEnum.SE002.name());
        }
        
        //Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.info("Could not determine file type.");

            throw new StorageException(ErrorCodeEnum.SE003.name());
        }

        //Get file original name
        String filename= new String();
        try {
            filename = file.getOriginalName();
        } catch (Exception e) {
            throw new StorageException(ErrorCodeEnum.SE004.name());
        }
        

        //return the file
        return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
        .body(resource);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:3000", "http://127.0.0.1:8080"})
    @RequestMapping(value = "/files", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fileList() throws JsonProcessingException{
        List<DBFile> files = new ArrayList<DBFile>();
        files = filesRepository.findAll();
        JsonNode jsonNode =  objectMapper.readTree(objectMapper.writeValueAsString(files));
        //removing id for security purposes
        for (JsonNode fileNode : jsonNode) {
            if (fileNode instanceof ObjectNode) {
                ObjectNode object = (ObjectNode) fileNode;
                object.remove("id");
                object.remove("name");
                object.remove("extension");
            }
        }

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(jsonNode));
    }
}
