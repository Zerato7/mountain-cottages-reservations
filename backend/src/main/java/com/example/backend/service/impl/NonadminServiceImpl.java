package com.example.backend.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.db.model.Nonadmin;
import com.example.backend.config.FileUploadProperties;
import com.example.backend.db.model.Host;
import com.example.backend.db.model.Tourist;
import com.example.backend.db.model.User;
import com.example.backend.db.repository.HostRepository;
import com.example.backend.db.repository.NonadminRepository;
import com.example.backend.db.repository.TouristRepository;
import com.example.backend.db.repository.UserRepository;
import com.example.backend.dto.PasswordChangeDTO;
import com.example.backend.dto.UserLoginDTO;
import com.example.backend.dto.UserRegistrationDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;
import com.example.backend.exception.AuthException;
import com.example.backend.exception.BackendServerException;
import com.example.backend.exception.DuplicateUserException;
import com.example.backend.exception.PasswordChangeException;
import com.example.backend.service.NonadminService;
import com.example.backend.util.EncryptionUtil;

@Service
public class NonadminServiceImpl implements NonadminService {

    private final PasswordEncoder passwordEncoder;
    private final EncryptionUtil encryptionUtil;

    private final FileUploadProperties fileUploadProperties;

    private final UserRepository userRepository;
    private final NonadminRepository nonadminRepository;
    private final HostRepository hostRepository;
    private final TouristRepository touristRepository;

    public NonadminServiceImpl(
            PasswordEncoder passwordEncoder,
            EncryptionUtil encryptionUtil,
            FileUploadProperties fileUploadProperties,
            UserRepository userRepository,
            NonadminRepository nonadminRepository,
            HostRepository hostRepository,
            TouristRepository touristRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.encryptionUtil = encryptionUtil;
        this.fileUploadProperties = fileUploadProperties;
        this.userRepository = userRepository;
        this.nonadminRepository = nonadminRepository;
        this.hostRepository = hostRepository;
        this.touristRepository = touristRepository;
    }

    @Override
    public Nonadmin registerNonadmin(UserRegistrationDTO dto, MultipartFile profilePicture) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateUserException("Korisničko ime već postoji.", "username");
        }
        if (nonadminRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateUserException("E-mail adresa već postoji.", "email");
        }

        switch(dto.getUserType()) {
            case TOURIST:
                Tourist tourist = new Tourist();
                mapUserRegistrationDTOToNonadmin(dto, profilePicture, tourist);
                return touristRepository.save(tourist);
            case HOST:
                Host host = new Host();
                mapUserRegistrationDTOToNonadmin(dto, profilePicture, host);
                return hostRepository.save(host);
            default:
                throw new AuthException("Unsupported user type: " + dto.getUserType());
        }
    }

    private void mapUserRegistrationDTOToNonadmin(UserRegistrationDTO dto, MultipartFile profilePicture, Nonadmin nonadmin) {
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

        String profilePicturePath;
        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                profilePicturePath = saveImageToFileSys(dto.getUsername(), profilePicture);
            } catch (IOException e) {
                throw new BackendServerException("Уписивање слике није успело.");
            }
        } else {
            profilePicturePath = dto.getGender().equals("M") ? 
                                fullPathName(fileUploadProperties.getDefaultMale()) :
                                fullPathName(fileUploadProperties.getDefaultFemale());
        }
        nonadmin.setProfilePicturePath(profilePicturePath);
    }

    private String saveImageToFileSys(String username, MultipartFile image) throws IOException {
        String fileName = username + String.valueOf(System.currentTimeMillis()) + 
                          '.' + FilenameUtils.getExtension(image.getOriginalFilename());
        Path folder = Paths.get(fileUploadProperties.getDir());
        Files.createDirectories(folder);
        Path fullPath = folder.resolve(fileName);
        Files.copy(image.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
        return fullPath.toString();
    }

    private String fullPathName(String fileName) {
        return Paths.get(fileName).toString();
    }

    @Override
    public NonadminResponseDTO loginNonadmin(UserLoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
            new AuthException("Nevalidni korisničko ime ili lozinka.")
        );
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthException("Nevalidni korisničko ime ili lozinka.");
        }
        Nonadmin nonadmin = nonadminRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
            new AuthException("Nevalidni korisničko ime ili lozinka.")
        );
        if (!nonadmin.isNonadmin()) {
            throw new AuthException("Korisnik nije ni turista ni vlasnik.");
        }
        NonadminResponseDTO response = new NonadminResponseDTO();
        response.setId(nonadmin.getId());
        response.setUsername(nonadmin.getUsername());
        response.setUserType(nonadmin.getUserType());
        response.setFirstName(nonadmin.getFirstName());
        response.setLastName(nonadmin.getLastName());
        response.setGender(nonadmin.getGender());
        response.setAddress(nonadmin.getAddress());
        response.setPhoneNumber(nonadmin.getPhoneNumber());
        response.setEmail(nonadmin.getEmail());
        response.setProfilePicturePath(nonadmin.getProfilePicturePath());
        response.setCreditCardNumberLast4Digits(nonadmin.getCreditCardNumberLast4Digits());
        return response;
    }

    @Override
    public void changePassword(PasswordChangeDTO dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(() ->
            new PasswordChangeException("Корисник чија се лозинка покушава променити, не постоји.")
        );
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new PasswordChangeException("Промена лозинке није успела.");
        }
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
            throw new PasswordChangeException("Промена лозинке није успела.");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }
    
}
