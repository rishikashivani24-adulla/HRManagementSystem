package com.example.hrmanagementsystem.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    private final String uploadDir = "uploads";

    // File Upload
    @PostMapping("/api/files/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Please select a file");
            }

            Files.createDirectories(Paths.get(uploadDir));

            String fileName = UUID.randomUUID() + "_"
                    + Paths.get(file.getOriginalFilename())
                            .getFileName()
                            .toString();

            Path filePath = Paths.get(uploadDir).resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return ResponseEntity.ok(
                    "File uploaded successfully: " + fileName
            );

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("File upload failed");
        }
    }

    // File Download
    @GetMapping("/api/files/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String fileName) {

        try {
            Path filePath = Paths.get(uploadDir)
                    .resolve(fileName)
                    .normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\""
                                    + resource.getFilename() + "\""
                    )
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
