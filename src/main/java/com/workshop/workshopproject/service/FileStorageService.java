package com.workshop.workshopproject.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public String storeFile(MultipartFile file, String username);
    public Resource loadFileAsResource(String fileName);
}
