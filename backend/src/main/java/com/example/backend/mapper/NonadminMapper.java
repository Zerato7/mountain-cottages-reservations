package com.example.backend.mapper;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.db.model.Nonadmin;
import com.example.backend.dto.RequestDTO.UserEditDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;
import com.example.backend.exception.BackendServerException;
import com.example.backend.util.EncryptionUtil;
import com.example.backend.util.ImageUtil;

@Component
public class NonadminMapper {

    private final EncryptionUtil encryptionUtil;
    private final ImageUtil imageUtil;

    public NonadminMapper(
        EncryptionUtil encryptionUtil,
        ImageUtil imageUtil
    ) {
        this.encryptionUtil = encryptionUtil;
        this.imageUtil = imageUtil;
    }
    
    public NonadminResponseDTO toResDto(Nonadmin nonadmin) {
        NonadminResponseDTO nonadminResponseDTO = new NonadminResponseDTO();
        nonadminResponseDTO.setId(nonadmin.getId());
        nonadminResponseDTO.setUsername(nonadmin.getUsername());
        nonadminResponseDTO.setUserType(nonadmin.getUserType());
        nonadminResponseDTO.setStatus(nonadmin.getStatus());
        nonadminResponseDTO.setFirstName(nonadmin.getFirstName());
        nonadminResponseDTO.setLastName(nonadmin.getLastName());
        nonadminResponseDTO.setGender(nonadmin.getGender());
        nonadminResponseDTO.setAddress(nonadmin.getAddress());
        nonadminResponseDTO.setPhoneNumber(nonadmin.getPhoneNumber());
        nonadminResponseDTO.setEmail(nonadmin.getEmail());
        nonadminResponseDTO.setProfilePicturePath(nonadmin.getProfilePicturePath());
        nonadminResponseDTO.setCreditCardNumberLast4Digits(nonadmin.getCreditCardNumberLast4Digits());
        return nonadminResponseDTO;
    }

    public void editFromDto(Nonadmin nonadmin, UserEditDTO dto, MultipartFile profilePicture) {
        if (dto.getUsername() != null) {
            nonadmin.setUsername(dto.getUsername());
        }

        nonadmin.setFirstName(dto.getFirstName());
        nonadmin.setLastName(dto.getLastName());

        if (dto.getGender() != null) {
            nonadmin.setGender(dto.getGender());
        }
        
        nonadmin.setAddress(dto.getAddress());
        nonadmin.setPhoneNumber(dto.getPhoneNumber());
        nonadmin.setEmail(dto.getEmail());
        
        if (dto.getCreditCardNumber() != null && !dto.getCreditCardNumber().isEmpty()) {
            nonadmin.setCreditCardNumber(encryptionUtil.encrypt(dto.getCreditCardNumber()));
            if (dto.getCreditCardNumber().length() >= 4) {
                nonadmin.setCreditCardNumberLast4Digits(dto.getCreditCardNumber().substring(dto.getCreditCardNumber().length() - 4));
            }
        }
        
        if (dto.getEditProfilePicture()) {
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

}
