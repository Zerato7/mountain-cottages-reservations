package com.example.backend.db.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Cottage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer capacity;

    private String photosFolderPath;

    @Column(length = 500)
    private String services;
    
    @Column(nullable = false)
    private Double winterPriceAdult;

    @Column(nullable = false)
    private Double winterPriceChild;

    @Column(nullable = false)
    private Double summerPriceAdult;

    @Column(nullable = false)
    private Double summerPriceChild;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Host owner;

    @OneToMany(mappedBy = "cottage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CottagePhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "cottage")
    private List<Reservation> reservations = new ArrayList<>();

    // Getters and Setters

    public Long getId() {
        return id;
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

    public String getPhotosFolderPath() {
        return photosFolderPath;
    }

    public void setPhotosFolderPath(String photosFolderPath) {
        this.photosFolderPath = photosFolderPath;
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

    public Host getOwner() {
        return owner;
    }

    public void setOwner(Host owner) {
        this.owner = owner;
    }

    public List<CottagePhoto> getPhotos() {
        return photos;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
    
}
