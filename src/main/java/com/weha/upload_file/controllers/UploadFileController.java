package com.weha.upload_file.controllers;

import com.weha.upload_file.dtos.FileInfo;
import com.weha.upload_file.services.UploadFileService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
@Tag(name = "Upload File", description = "APIs for managing file")
public class UploadFileController {

    private final UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadFile(
            @Parameter(description = "File to upload", required = true)
            @ModelAttribute("files") MultipartFile[] files
    ) {
        try {
            List<String> fileNames = new ArrayList<>();
            Arrays.stream(files).forEach(file -> {
                uploadFileService.save(file);
                fileNames.add(file.getOriginalFilename());
            });
            return ResponseEntity.ok(fileNames);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = new ArrayList<>();
        uploadFileService.loadAll().forEach(path -> {
            String fileName = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(
                            UploadFileController.class,
                            "getFile",
                            path.getFileName().toString())
                    .build()
                    .toString();
            fileInfos.add(new FileInfo(fileName, url));
        });
        return ResponseEntity.ok(fileInfos);
    }

    @GetMapping("{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        Resource file = uploadFileService.load(fileName);
        return ResponseEntity.ok(file);
    }
}