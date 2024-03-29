package com.fadgiras.exos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fadgiras.exos.exception.StorageException;
import com.fadgiras.exos.model.DBFile;
import com.fadgiras.exos.repository.FilesRepository;
import com.fadgiras.exos.enums.ErrorCodeEnum;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    @Autowired
    private FilesRepository filesRepository;

    public void uploadFile(MultipartFile file) throws StorageException {

        if (file.isEmpty()) {

            throw new StorageException(ErrorCodeEnum.SE101.name());
        }

        //Verify File ext
        String fileExt = file.getOriginalFilename().split("\\.")[1];
        String[] authorizedExt = {"pdf","doc","docx","txt","odt"};
        //if the File isn't an authorized one : yeet an exception
        if(!Arrays.asList(authorizedExt).contains(fileExt)){
            throw new StorageException(ErrorCodeEnum.SE005.name());
        }

        try {
            final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String uuid = UUID.randomUUID().toString();
            String fileName = sdf1.format(timestamp).toString()+"_"+uuid;

            var is = file.getInputStream();

            Files.copy(is, Paths.get(path + fileName+"."+fileExt),StandardCopyOption.REPLACE_EXISTING);

            //Creating object in DB

            DBFile fileToSave = new DBFile(uuid, file.getOriginalFilename(), fileName, Date.valueOf(sdf2.format(timestamp).toString()), fileExt);

            filesRepository.save(fileToSave);

            
        } catch (IOException e) {

            throw new StorageException(ErrorCodeEnum.SE102.name());
        }
    }

    //TODO Maybe add update (delete and add)

    public void deleteFile(Long id) throws StorageException {
        

        //Find the file to delete
        Optional<DBFile> optionalEntity =  filesRepository.findById(id);
        DBFile file = optionalEntity.get();
        String filename  = file.getName();
        String fileExt = file.getExtension();
        //Deleting file
        try {
            //Delete on disk
            Files.deleteIfExists(Paths.get(path+filename+"."+fileExt));

            //Delete object in DB
            filesRepository.delete(file);

        } catch (IOException e) {

            throw new StorageException(ErrorCodeEnum.SE103.name());
            
        }
    }

    public FileSystemResource downloadFile(long id) throws MalformedURLException, StorageException {

        Optional<DBFile> optionalEntity =  filesRepository.findById(id);
        DBFile file = optionalEntity.get();
        String filename  = file.getName();
        String fileExt = file.getExtension();
        String fullFilePath = path+filename+"."+fileExt;

        FileSystemResource resource = new FileSystemResource(fullFilePath);
        if (resource.exists()) {
            return resource;
        } else {
            throw new StorageException(ErrorCodeEnum.SE104.name());
        }
    }
}
