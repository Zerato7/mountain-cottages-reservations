package com.example.backend.dto.RequestDTO;

import com.example.backend.db.model.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserRegistrationDTO {
    
    @NotBlank(message = "Корисничко име је обавезно.")
    private String username;

    @NotBlank(message = "Лозинка је обавезна.")
    private String password;

    @NotBlank(message = "Име је обавезно.")
    private String firstName;

    @NotBlank(message = "Презиме је обавезно.")
    private String lastName;

    @Pattern(regexp = "^[МЖ]$", message = "Пол мора бити 'М' или 'Ж'.")
    @NotBlank(message = "Пол је обавезан.")
    private String gender;

    @NotBlank(message = "Адреса је обавезна.")
    private String address;

    @NotBlank(message = "Контакт телефон је обавезан.")
    private String phoneNumber;

    @Email(message = "Е-мејл мора бити валидан.")
    @NotBlank(message = "Е-мејл је обавезан.")
    private String email;

    @NotBlank(message = "Број кредитне картице је обавезан.")
    private String creditCardNumber;

    @NotNull(message = "Тип корисника је обавезан.")
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
