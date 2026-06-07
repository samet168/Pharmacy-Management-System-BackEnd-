package com.example.pharmacy.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileService {

    private final Path root = Paths.get("uploads");

    public String saveFile(MultipartFile file) {

        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            String fileName =
                    System.currentTimeMillis() + "_" +
                            file.getOriginalFilename();

            Files.copy(file.getInputStream(),
                    this.root.resolve(fileName));

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Upload failed!");
        }
    }

    public void deleteFile(String fileName) {

        try {
            if (fileName != null) {
                Files.deleteIfExists(root.resolve(fileName));
            }
        } catch (IOException e) {
            throw new RuntimeException("Delete failed: " + e.getMessage());
        }
    }
}