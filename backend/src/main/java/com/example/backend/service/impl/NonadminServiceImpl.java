package com.example.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.db.model.Nonadmin;
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
import com.example.backend.exception.DuplicateUserException;
import com.example.backend.exception.PasswordChangeException;
import com.example.backend.service.NonadminService;
import com.example.backend.util.EncryptionUtil;

@Service
public class NonadminServiceImpl implements NonadminService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NonadminRepository nonadminRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private TouristRepository touristRepository;

    @Override
    public Nonadmin registerNonadmin(UserRegistrationDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateUserException("Korisničko ime već postoji.", "username");
        }
        if (nonadminRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateUserException("E-mail adresa već postoji.", "email");
        }

        switch(dto.getUserType()) {
            case TOURIST:
                Tourist tourist = new Tourist();
                mapUserRegistrationDTOToNonadmin(dto, tourist);
                return touristRepository.save(tourist);
            case HOST:
                Host host = new Host();
                mapUserRegistrationDTOToNonadmin(dto, host);
                return hostRepository.save(host);
            default:
                throw new IllegalArgumentException("Unsupported user type: " + dto.getUserType());
        }
    }

    private void mapUserRegistrationDTOToNonadmin(UserRegistrationDTO dto, Nonadmin nonadmin) {
        nonadmin.setUsername(dto.getUsername());
        nonadmin.setPassword(passwordEncoder.encode(dto.getPassword()));
        nonadmin.setFirstName(dto.getFirstName());
        nonadmin.setLastName(dto.getLastName());
        nonadmin.setGender(dto.getGender());
        nonadmin.setAddress(dto.getAddress());
        nonadmin.setPhoneNumber(dto.getPhoneNumber());
        nonadmin.setEmail(dto.getEmail());
        nonadmin.setProfilePicturePath(dto.getProfilePicturePath());
        nonadmin.setCreditCardNumber(encryptionUtil.encrypt(dto.getCreditCardNumber()));
        if (dto.getCreditCardNumber().length() >= 4) {
            nonadmin.setCreditCardNumberLast4Digits(dto.getCreditCardNumber().substring(dto.getCreditCardNumber().length() - 4));
        }
        nonadmin.setUserType(dto.getUserType());
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
