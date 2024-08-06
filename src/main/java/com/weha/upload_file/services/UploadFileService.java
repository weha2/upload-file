package com.weha.upload_file.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface UploadFileService {
    void init();

    void save(MultipartFile file);

    Resource load(String name);

    void deleteAll();

    Stream<Path> loadAll();
}
