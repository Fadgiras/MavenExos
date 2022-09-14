package com.fadgiras.exos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fadgiras.exos.exception.StorageException;
import com.fadgiras.exos.model.File;
import com.fadgiras.exos.repository.FilesRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    @Autowired
    private FilesRepository filesRepository;

    public void uploadFile(MultipartFile file) {

        if (file.isEmpty()) {

            throw new StorageException("Failed to store empty file");
        }

        try {
            //Maybe add file name hash ?
            //TODO Create object in DB only after success:  avoid creating inexisting object
            final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String fileName = sdf1.format(timestamp).toString()+"_"+file.getOriginalFilename();

            var is = file.getInputStream();

            Files.copy(is, Paths.get(path + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

            String msg ="Failed to store file :"+ file.getName().toString();

            throw new StorageException(msg, e);
        }
    }

    //TODO Add delete, download option, maybe update

    public void deleteFile(Long id) {
        

        //Find the file to delete
        Optional<File> optionalEntity =  filesRepository.findById(id);
        File file = optionalEntity.get();
        String filename  = file.getName();
        //Deleting file
        try {
            boolean result = Files.deleteIfExists(Paths.get(filename));
            if (result) {
                System.out.println("File is deleted!");
            } else {
                System.out.println("Sorry, unable to delete the file.");
            }
        } catch (IOException e) {
            String msg ="Failed to delete file :"+ file.getName().toString();

            throw new StorageException(msg, e);
            
        }
    }

    public void downloadFile(long id) {
        
    }

}
