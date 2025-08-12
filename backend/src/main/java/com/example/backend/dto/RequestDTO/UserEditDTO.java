package com.example.backend.dto.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserEditDTO {

    @NotNull(message = "ID неадмина је обавезан.")
    private Long id;

    @NotBlank(message = "Име је обавезно.")
    private String firstName;

    @NotBlank(message = "Презиме је обавезно.")
    private String lastName;

    @NotBlank(message = "Адреса је обавезна.")
    private String address;

    @NotBlank(message = "Контакт телефон је обавезан.")
    private String phoneNumber;

    @Email(message = "Е-мејл мора бити валидан.")
    @NotBlank(message = "Е-мејл је обавезан.")
    private String email;

    @NotNull(message = "Поље за потврду измене профилне слике је обавезно.")
    private Boolean editProfilePicture;

    // Can only be edited by admins and tourists themselfs

    private String creditCardNumber;

    // Can only be edited by admins
    
    private String username;
    
    @Pattern(regexp = "^[МЖ]$", message = "Пол мора бити 'М' или 'Ж'.")
    private String gender;

    // Getters and Setters

    public Long getId() {
        return id;
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

    public Boolean getEditProfilePicture() {
        return editProfilePicture;
    }

    public void setEditProfilePicture(Boolean editProfilePicture) {
        this.editProfilePicture = editProfilePicture;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
