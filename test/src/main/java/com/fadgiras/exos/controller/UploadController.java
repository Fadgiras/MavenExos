package com.fadgiras.exos.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private Resource resource;

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private StorageService storageService;
    //TODO changes the return values : won't do right in a SPA

    //TODO Restrict file extensions : pdf, doc, docx, txt
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public String upload(@RequestParam MultipartFile file) throws StorageException {

        storageService.uploadFile(file);

        return "redirect:/success.html";
    }


    @RequestMapping(value = "/fileDelete/{uuid}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value="uuid") String uuid) throws StorageException{
        DBFile file;
        try {
            Optional<DBFile> optionalEntity =  filesRepository.findFileByUUID(uuid);
            file = optionalEntity.get();        
        } catch (Exception e) {
            throw new StorageException(ErrorCodeEnum.SE001E.name());
        }

        storageService.deleteFile(file.getId());

        return "redirect:/success.html";
    
    }

    //Maybe switch from id to filename
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
}
