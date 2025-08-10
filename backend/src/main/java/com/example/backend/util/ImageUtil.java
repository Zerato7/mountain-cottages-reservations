package com.example.backend.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.config.FileUploadProperties;

@Component
public class ImageUtil {
    
    private final FileUploadProperties fileUploadProperties;

    public ImageUtil(
        FileUploadProperties fileUploadProperties
    ) {
        this.fileUploadProperties = fileUploadProperties;
    }

    public String saveImageToFileSys(String username, MultipartFile image) throws IOException {
        String fileName = username + String.valueOf(System.currentTimeMillis()) + 
                          '.' + FilenameUtils.getExtension(image.getOriginalFilename());
        Path folder = Paths.get(fileUploadProperties.getDir());
        Files.createDirectories(folder);
        Path fullPath = folder.resolve(fileName);
        Files.copy(image.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
        return normalizePath(fullPath.toString());
    }

    public String getDefaultImagePath(String gender) {
        return gender.equals("М") ? 
            fullPathName(fileUploadProperties.getDefaultMale()) :
            fullPathName(fileUploadProperties.getDefaultFemale());
    }
    
    private String fullPathName(String fileName) {
        return normalizePath(Paths.get(fileName).toString());
    }

    private String normalizePath(String filePath) {
        return filePath.replace(File.separatorChar, '/');
    }

}
