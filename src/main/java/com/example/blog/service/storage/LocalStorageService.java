package com.example.blog.service.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    @Value("${app.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename);

        if (!isAllowedExtension(extension)) {
            throw new RuntimeException("Invalid file extension: " + extension);
        }

        // TODO: Validate Content-Type as well

        String storedFileName = UUID.randomUUID().toString() + "." + extension;

        try (InputStream inputStream = file.getInputStream()) {
            Path targetLocation = Paths.get(uploadPath).resolve(storedFileName);
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + storedFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + originalFilename, e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains("."))
            return "";
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private boolean isAllowedExtension(String extension) {
        return List.of("jpg", "jpeg", "png", "webp", "gif").contains(extension);
    }
}
