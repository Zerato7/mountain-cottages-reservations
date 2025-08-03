package com.example.backend.dto.ResponseDTO;

public class NonadminResponseDTO extends UserResponseDTO {
    
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String phoneNumber;
    private String email;
    private String profilePicturePath;
    private String creditCardNumberLast4Digits;

    // Getters and Setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getCreditCardNumberLast4Digits() {
        return creditCardNumberLast4Digits;
    }

    public void setCreditCardNumberLast4Digits(String creditCardNumberLast4Digits) {
        this.creditCardNumberLast4Digits = creditCardNumberLast4Digits;
    }

}
