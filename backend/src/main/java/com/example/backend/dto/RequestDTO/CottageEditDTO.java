package com.example.backend.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class CottageEditDTO {

    @NotNull
    private Long id;

    @NotBlank(message = "Назив је обавезан.")
    private String name;

    @NotBlank(message = "Место је обавезно.")
    private String location;

    @Positive(message = "Капацитет мора бити позитиван број.")
    @NotNull(message = "Капацитет је обавезан.")
    private Integer capacity;

    @Size(max = 500, message = "Услуге може бити највише 500 карактера")
    private String services;
    
    @PositiveOrZero(message = "Цена ноћења зими за одрасле мора бити ненегативан број.")
    @NotNull(message = "Цена ноћења зими за одрасле је обавезна.")
    private Double winterPriceAdult;

    @PositiveOrZero(message = "Цена ноћења зими за децу мора бити ненегативан број.")
    @NotNull(message = "Цена ноћења зими за децу је обавезна.")
    private Double winterPriceChild;

    @PositiveOrZero(message = "Цена ноћења лети за одрасле мора бити ненегативан број.")
    @NotNull(message = "Цена ноћења лети за одрасле је обавезна.")
    private Double summerPriceAdult;

    @PositiveOrZero(message = "Цена ноћења лети за децу мора бити ненегативан број.")
    @NotNull(message = "Цена ноћења лети за децу је обавезна.")
    private Double summerPriceChild;

    @NotBlank(message = "Контакт телефон је обавезан.")
    private String phoneNumber;

    @NotNull(message = "Географска ширина је обавезна.")
    private Double latitude;

    @NotNull(message = "Географска дужина је обавезна.")
    private Double longitude;

    @NotNull(message = "Мора се навести да ли се бришу слике.")
    private Boolean imageDelete;

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

    public Boolean getImageDelete() {
        return imageDelete;
    }

    public void setImageDelete(Boolean imageDelete) {
        this.imageDelete = imageDelete;
    }
    
}
