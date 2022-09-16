package com.fadgiras.exos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fadgiras.exos.exception.StorageException;
import com.fadgiras.exos.model.DBFile;
import com.fadgiras.exos.repository.FilesRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.security.*;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader; 
    }

    public static String hash(String input){
        byte[] msg = input.getBytes();
        byte[] hash = null;

        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(msg);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        StringBuilder strBuilder = new StringBuilder();
        for(byte b:hash)
        {
            strBuilder.append(String.format("%02x", b));
        }
        String strHash = strBuilder.toString();

        return strHash;
    }    
    //TODO Add proper extensions to files
    public void uploadFile(MultipartFile file) {

        if (file.isEmpty()) {

            throw new StorageException("Failed to store empty file");
        }

        try {
            final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String fileName = sdf1.format(timestamp).toString()+"_"+hash(file.getOriginalFilename());

            var is = file.getInputStream();

            Files.copy(is, Paths.get(path + fileName),StandardCopyOption.REPLACE_EXISTING);

            //Creating object in DB

            DBFile fileToSave = new DBFile(file.getOriginalFilename(), fileName, Date.valueOf(sdf2.format(timestamp).toString()));

            filesRepository.save(fileToSave);

            
        } catch (IOException e) {

            String msg ="Failed to store file :"+ file.getName().toString();

            throw new StorageException(msg, e);
        }
    }

    //TODO Add delete, download option, maybe update

    public void deleteFile(Long id) {
        

        //Find the file to delete
        Optional<DBFile> optionalEntity =  filesRepository.findById(id);
        DBFile file = optionalEntity.get();
        String filename  = file.getName();
        //Deleting file
        try {
            //Delete on disk
            Files.deleteIfExists(Paths.get(path+filename));

            //Delete object in DB
            filesRepository.delete(file);

        } catch (IOException e) {
            String msg ="Failed to delete file :"+ file.getName().toString();

            throw new StorageException(msg, e);
            
        }
    }

    public Resource downloadFile(long id) throws MalformedURLException {
        Optional<DBFile> optionalEntity =  filesRepository.findById(id);
        DBFile file = optionalEntity.get();

        File filetest = new File("bouh");

        String filename  = file.getName();
        Resource resource = resourceLoader.getResource(path+filename);

        FileSystemResource resource2 = new FileSystemResource(path+filename);

        System.out.println(resource2.getFile().toString());

        System.out.println(resource.toString());
        //TODO absolute path
        if (resource2.exists()) {
            System.out.println("rsc exist");
            return resource2;
        } else {
            throw new StorageException("Resource does not exist : " + path+filename);
        }
    }
}
