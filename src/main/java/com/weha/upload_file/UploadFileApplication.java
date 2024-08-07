package com.weha.upload_file;

import com.weha.upload_file.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UploadFileApplication implements CommandLineRunner {

    @Autowired
    private UploadFileService uploadFileService;

    public static void main(String[] args) {
        SpringApplication.run(UploadFileApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //uploadFileService.deleteAll();
        uploadFileService.init();
    }
}
