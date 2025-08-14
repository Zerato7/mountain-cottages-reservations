package com.example.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file-upload")
public class FileUploadProperties {
    
    private String dir;
    private String profileSubDir;
    private String cottagePhotosSubDir;
    private String defaultMale;
    private String defaultFemale;

    // Gettters and Setters

    public String getProfileDir() {
        return dir + profileSubDir;
    }

    public String getCottagePhotosDir() {
        return dir + cottagePhotosSubDir;
    }
    
    public String getDir() {
        return dir;
    }
    
    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getProfileSubDir() {
        return profileSubDir;
    }

    public void setProfileSubDir(String profileSubDir) {
        this.profileSubDir = profileSubDir;
    }

    public String getCottagePhotosSubDir() {
        return cottagePhotosSubDir;
    }

    public void setCottagePhotosSubDir(String cottagePhotosSubDir) {
        this.cottagePhotosSubDir = cottagePhotosSubDir;
    }

    public String getDefaultMale() {
        return defaultMale;
    }

    public void setDefaultMale(String defaultMale) {
        this.defaultMale = defaultMale;
    }

    public String getDefaultFemale() {
        return defaultFemale;
    }

    public void setDefaultFemale(String defaultFemale) {
        this.defaultFemale = defaultFemale;
    }

}
