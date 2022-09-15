package com.fadgiras.exos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fadgiras.exos.exception.StorageException;
import com.fadgiras.exos.service.StorageService;

@Controller
@RequestMapping("/api")
public class UploadController {
    @Autowired
    private StorageService storageService;
    //TODO Remap all this into api/..
    //TODO changes the return values : won't do right in a SPA

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public String upload(@RequestParam MultipartFile file) {

        storageService.uploadFile(file);

        return "redirect:/success.html";
    }

    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e) {

        return "redirect:/failure.html";
    }

    @RequestMapping(value = "/fileDelete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value="id") Long fileId){

        storageService.deleteFile(fileId);

        return "redirect:/success.html";
    
    }
}
