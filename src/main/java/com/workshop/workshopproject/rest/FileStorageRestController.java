package com.workshop.workshopproject.rest;


import com.workshop.workshopproject.entity.User;
import com.workshop.workshopproject.payload.UploadFileResponse;
import com.workshop.workshopproject.properties.FileStorageProperties;
import com.workshop.workshopproject.queries.QueryManager;
import com.workshop.workshopproject.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class FileStorageRestController {

    private Logger logger = LoggerFactory.getLogger(FileStorageRestController.class);
    private FileStorageService fileStorageService;
    private FileStorageProperties fileStorageProperties;
    private ServletContext context;
    private QueryManager queryManager;


    @Autowired
    public FileStorageRestController(FileStorageService fileStorageService, FileStorageProperties fileStorageProperties, ServletContext context, QueryManager queryManager) {
        this.fileStorageService = fileStorageService;
        this.fileStorageProperties = fileStorageProperties;
        this.context = context;
        this.queryManager = queryManager;
    }

    @PostMapping("uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
//        User user = queryManager.getUser((String)jsonHeader.get("token"));
//        String username = user.getUsername();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);// prob
        String fileName = fileStorageService.storeFile(file,username);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/downloadFile/")
                .path(username+"/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("downloadFile/{username}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,@PathVariable String username, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(username+"/"+fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
