package com.example.backend.dto.ResponseDTO;

public class GeneralInfoDTO {
    
    private Long cottageNum;
    
    private Long hostNum;

    private Long touristNum;

    private Long reservation24HNum;

    private Long reservation7DNum;

    private Long reservation30DNum;

    // Getters and Setters

    public Long getCottageNum() {
        return cottageNum;
    }

    public void setCottageNum(Long cottageNum) {
        this.cottageNum = cottageNum;
    }

    public Long getHostNum() {
        return hostNum;
    }

    public void setHostNum(Long hostNum) {
        this.hostNum = hostNum;
    }

    public Long getTouristNum() {
        return touristNum;
    }

    public void setTouristNum(Long touristNum) {
        this.touristNum = touristNum;
    }

    public Long getReservation24HNum() {
        return reservation24HNum;
    }

    public void setReservation24HNum(Long reservation24hNum) {
        reservation24HNum = reservation24hNum;
    }

    public Long getReservation7DNum() {
        return reservation7DNum;
    }

    public void setReservation7DNum(Long reservation7dNum) {
        reservation7DNum = reservation7dNum;
    }

    public Long getReservation30DNum() {
        return reservation30DNum;
    }

    public void setReservation30DNum(Long reservation30dNum) {
        reservation30DNum = reservation30dNum;
    }

}
