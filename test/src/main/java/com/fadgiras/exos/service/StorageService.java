package com.fadgiras.exos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fadgiras.exos.exception.StorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    public void uploadFile(MultipartFile file) {

        if (file.isEmpty()) {

            throw new StorageException("Failed to store empty file");
        }

        try {
            //Maybe add file name hash ?
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
}
