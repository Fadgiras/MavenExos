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

import com.fadgiras.exos.exception.StorageException;
import com.fadgiras.exos.model.DBFile;
import com.fadgiras.exos.repository.FilesRepository;
import com.fadgiras.exos.service.StorageService;
import com.fadgiras.exos.exception.GlobalExceptionHandler;

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
    //TODO code erreurs type : SE01 etc à la place des exceptions

    //TODO Restrict file extensions : pdf, doc, docx, txt
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public String upload(@RequestParam MultipartFile file) throws StorageException {

        storageService.uploadFile(file);

        return "redirect:/success.html";
    }


    @RequestMapping(value = "/fileDelete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value="id") Long fileId) throws StorageException{

        storageService.deleteFile(fileId);

        return "redirect:/success.html";
    
    }

    //Maybe switch from id to filename
    @RequestMapping(value = "/fileDownload/{id}", method = RequestMethod.GET)
    public ResponseEntity download(@PathVariable Long id, HttpServletRequest request) throws MalformedURLException, StorageException {

        //Loading file
        DBFile file;
        //TODO Use this UUID file finder to switch from ID to UUID in URL
        Optional<DBFile> optionalEntity =  filesRepository.findFileByUUID("236c4a1e-b11b-4015-9d83-6ec5a8585edc");
        file = optionalEntity.get();

        System.out.println(file.toString());

        try {
            optionalEntity =  filesRepository.findById(id);
            file = optionalEntity.get();
        } catch (Exception e) {
            throw new StorageException("Failed to load file", "SE001");
        }

        try {
            resource  = storageService.downloadFile(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new StorageException("The resource couldn't be loaded","SE002");
        }
        
        //Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.info("Could not determine file type.");

            String msg ="Failed to retrieve content-type : "+ resource.toString();

            throw new StorageException(msg, e, "SE003");
        }

        //Get file original name
        String filename= new String();
        try {
            filename = file.getOriginalName();
        } catch (Exception e) {
            String msg ="Failed to get filename : ";

            throw new StorageException(msg, e, "SE004");
        }
        

        //return the file
        return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
        .body(resource);
    }
}
