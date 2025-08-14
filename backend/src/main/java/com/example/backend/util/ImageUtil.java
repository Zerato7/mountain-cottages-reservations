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

    public String saveProfileToFileSys(String name, MultipartFile image) throws IOException {
        return saveImageToFileSys(name, fileUploadProperties.getProfileDir(), image);
    }

    public String getDefaultProfilePath(String gender) {
        return gender.equals("М") ? 
            fullPathName(fileUploadProperties.getDefaultMale()) :
            fullPathName(fileUploadProperties.getDefaultFemale());
    }

    public String createFolder(String name) throws IOException {
        Path folder = Paths.get(fileUploadProperties.getCottagePhotosDir() + "/" + name);
        Files.createDirectories(folder);
        return normalizePath(folder.toString());
    }

    public String saveImageToFileSys(String name, String folderName, MultipartFile image) throws IOException {
        String fileName = name + String.valueOf(System.currentTimeMillis()) + 
                          '.' + FilenameUtils.getExtension(image.getOriginalFilename());
        Path folder = Paths.get(folderName);
        Files.createDirectories(folder);
        Path fullPath = folder.resolve(fileName);
        Files.copy(image.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
        return normalizePath(fullPath.toString());
    }
    
    private String fullPathName(String fileName) {
        return normalizePath(Paths.get(fileName).toString());
    }

    private String normalizePath(String filePath) {
        return filePath.replace(File.separatorChar, '/');
    }

}
