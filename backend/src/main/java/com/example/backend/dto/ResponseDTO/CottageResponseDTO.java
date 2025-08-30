package com.example.backend.dto.ResponseDTO;

import java.util.List;

public class CottageResponseDTO {
    
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private String services;
    private Double winterPriceAdult;
    private Double winterPriceChild;
    private Double summerPriceAdult;
    private Double summerPriceChild;
    private String phoneNumber;
    private Double latitude;
    private Double longitude;
    private Long ownerId;

    private List<CottagePhotoResponseDTO> photoPaths;
    private List<ReservationResponseDTO> reservations;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Double getWinterPriceAdult() {
        return winterPriceAdult;
    }

    public void setWinterPriceAdult(Double winterPriceAdult) {
        this.winterPriceAdult = winterPriceAdult;
    }

    public Double getWinterPriceChild() {
        return winterPriceChild;
    }

    public void setWinterPriceChild(Double winterPriceChild) {
        this.winterPriceChild = winterPriceChild;
    }

    public Double getSummerPriceAdult() {
        return summerPriceAdult;
    }

    public void setSummerPriceAdult(Double summerPriceAdult) {
        this.summerPriceAdult = summerPriceAdult;
    }

    public Double getSummerPriceChild() {
        return summerPriceChild;
    }

    public void setSummerPriceChild(Double summerPriceChild) {
        this.summerPriceChild = summerPriceChild;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<CottagePhotoResponseDTO> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<CottagePhotoResponseDTO> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public List<ReservationResponseDTO> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationResponseDTO> reservations) {
        this.reservations = reservations;
    }

}
