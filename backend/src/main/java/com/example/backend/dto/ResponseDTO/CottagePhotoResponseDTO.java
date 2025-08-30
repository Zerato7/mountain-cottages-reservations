package com.example.backend.dto.ResponseDTO;

public class CottagePhotoResponseDTO {
    
    int position;
    String photoPath;

    // Getters and Setters

    public int getPosition() {
        return position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

}
