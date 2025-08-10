package com.example.backend.mapper;

import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.db.model.Nonadmin;
import com.example.backend.db.model.UserStatus;
import com.example.backend.dto.RequestDTO.UserRegistrationDTO;
import com.example.backend.exception.BackendServerException;
import com.example.backend.util.EncryptionUtil;
import com.example.backend.util.ImageUtil;

@Component
public class RegistrationMapper {
    
    private final PasswordEncoder passwordEncoder;
    
    private final EncryptionUtil encryptionUtil;
    private final ImageUtil imageUtil;

    public RegistrationMapper(
        PasswordEncoder passwordEncoder,
        EncryptionUtil encryptionUtil,
        ImageUtil imageUtil
    ) {
        this.passwordEncoder = passwordEncoder;
        this.encryptionUtil = encryptionUtil;
        this.imageUtil = imageUtil;
    }
    
    public void toEntity(UserRegistrationDTO dto, MultipartFile profilePicture, Nonadmin nonadmin) {
        nonadmin.setUsername(dto.getUsername());
        nonadmin.setPassword(passwordEncoder.encode(dto.getPassword()));
        nonadmin.setFirstName(dto.getFirstName());
        nonadmin.setLastName(dto.getLastName());
        nonadmin.setGender(dto.getGender());
        nonadmin.setAddress(dto.getAddress());
        nonadmin.setPhoneNumber(dto.getPhoneNumber());
        nonadmin.setEmail(dto.getEmail());
        nonadmin.setCreditCardNumber(encryptionUtil.encrypt(dto.getCreditCardNumber()));
        if (dto.getCreditCardNumber().length() >= 4) {
            nonadmin.setCreditCardNumberLast4Digits(dto.getCreditCardNumber().substring(dto.getCreditCardNumber().length() - 4));
        }
        nonadmin.setUserType(dto.getUserType());
        nonadmin.setStatus(UserStatus.PENDING);

        String profilePicturePath;
        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                profilePicturePath = imageUtil.saveImageToFileSys(dto.getUsername(), profilePicture);
            } catch (IOException e) {
                throw new BackendServerException("Уписивање слике није успело.");
            }
        } else {
            profilePicturePath = imageUtil.getDefaultImagePath(dto.getGender());
        }
        nonadmin.setProfilePicturePath(profilePicturePath);
    }

}
