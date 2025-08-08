package com.example.backend.dto;

import com.example.backend.db.model.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserRegistrationDTO {
    
    @NotBlank(message = "Korisničko ime je obavezno")
    private String username;

    @NotBlank(message = "Lozinka je obavezna")
    private String password;

    @NotBlank(message = "Ime je obavezno")
    private String firstName;

    @NotBlank(message = "Prezime je obavezno")
    private String lastName;

    @Pattern(regexp = "^[MŽ]$", message = "Pol mora biti 'M' ili 'Ž'")
    @NotBlank(message = "Pol je obavezan")
    private String gender;

    @NotBlank(message = "Adresa je obavezna")
    private String address;

    @NotBlank(message = "Kontakt telefon je obavezan")
    private String phoneNumber;

    @Email(message = "E-mail mora biti validan")
    @NotBlank(message = "E-mail je obavezan")
    private String email;

    @NotBlank(message = "Broj kreditne kartice je obavezan")
    private String creditCardNumber;

    @NotNull(message = "Tip korisnika je obavezan")
    private UserType userType;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}
