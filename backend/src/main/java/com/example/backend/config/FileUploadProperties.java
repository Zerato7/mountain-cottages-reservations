package com.example.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file-upload")
public class FileUploadProperties {
    
    private String dir;
    private String defaultMale;
    private String defaultFemale;

    // Gettters and Setters

    public String getDir() {
        return dir;
    }
    public void setDir(String dir) {
        this.dir = dir;
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
