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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fadgiras.exos.exception.StorageException;
import com.fadgiras.exos.model.File;
import com.fadgiras.exos.repository.FilesRepository;
import com.fadgiras.exos.service.StorageService;

@Controller
@RequestMapping("/api")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private Resource resource;

    @Autowired
    private FilesRepository filesRepository;

    @Autowired
    private StorageService storageService;
    //TODO changes the return values : won't do right in a SPA

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public String upload(@RequestParam MultipartFile file) {

        storageService.uploadFile(file);

        return "redirect:/success.html";
    }


    @RequestMapping(value = "/fileDelete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value="id") Long fileId){

        storageService.deleteFile(fileId);

        return "redirect:/success.html";
    
    }

    //Maybe switch from id to filename
    @RequestMapping(value = "/fileDownload/{id}", method = RequestMethod.GET)
    public ResponseEntity download(@PathVariable Long id, HttpServletRequest request) throws MalformedURLException {

        try {
            resource  = storageService.downloadFile(id);
        } catch (Exception e) {
            throw new StorageException("The resource couldn't be loaded");
        }
        
        //Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            System.out.println("Content type ok");
        } catch (IOException e) {
            logger.info("Could not determine file type.");

            String msg ="Failed to retrieve content-type : "+ resource.toString();

            throw new StorageException(msg, e);
        }

        //Get file original name
        String filename= new String();
        try {
            Optional<File> optionalEntity =  filesRepository.findById(id);
            File file = optionalEntity.get();
            filename = file.getOriginalName();
        } catch (Exception e) {
            String msg ="Failed to get filename : ";

            throw new StorageException(msg, e);
        }
        

        //return the file
        return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
        .body(resource);
    }
}
