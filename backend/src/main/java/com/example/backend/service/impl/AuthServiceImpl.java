package com.example.backend.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.config.FileUploadProperties;
import com.example.backend.db.model.Host;
import com.example.backend.db.model.Nonadmin;
import com.example.backend.db.model.Tourist;
import com.example.backend.db.model.User;
import com.example.backend.db.model.UserStatus;
import com.example.backend.db.repository.HostRepository;
import com.example.backend.db.repository.NonadminRepository;
import com.example.backend.db.repository.TouristRepository;
import com.example.backend.db.repository.UserRepository;
import com.example.backend.dto.RequestDTO.PasswordChangeDTO;
import com.example.backend.dto.RequestDTO.UserLoginDTO;
import com.example.backend.dto.RequestDTO.UserRegistrationDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;
import com.example.backend.exception.AuthException;
import com.example.backend.exception.BadRequestException;
import com.example.backend.exception.DuplicateUserException;
import com.example.backend.exception.PasswordChangeException;
import com.example.backend.mapper.NonadminMapper;
import com.example.backend.mapper.RegistrationMapper;
import com.example.backend.service.AuthService;
import com.example.backend.util.EncryptionUtil;

@Service
public class AuthServiceImpl implements AuthService {
    
    private final PasswordEncoder passwordEncoder;

    private final NonadminMapper nonadminMapper;
    private final RegistrationMapper registrationMapper;
    
    private final UserRepository userRepository;
    private final NonadminRepository nonadminRepository;
    private final HostRepository hostRepository;
    private final TouristRepository touristRepository;

    public AuthServiceImpl(
            PasswordEncoder passwordEncoder,
            EncryptionUtil encryptionUtil,
            FileUploadProperties fileUploadProperties,
            NonadminMapper nonadminMapper,
            RegistrationMapper registrationMapper,
            UserRepository userRepository,
            NonadminRepository nonadminRepository,
            HostRepository hostRepository,
            TouristRepository touristRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.nonadminMapper = nonadminMapper;
        this.registrationMapper = registrationMapper;
        this.userRepository = userRepository;
        this.nonadminRepository = nonadminRepository;
        this.hostRepository = hostRepository;
        this.touristRepository = touristRepository;
    }

    @Override
    public Nonadmin registerNonadmin(UserRegistrationDTO dto, MultipartFile profilePicture) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateUserException("Корисничко име већ постоји.", "username");
        }
        if (nonadminRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateUserException("Е-мејл адреса већ постоји.", "email");
        }

        switch(dto.getUserType()) {
            case TOURIST:
                Tourist tourist = new Tourist();
                registrationMapper.toEntity(dto, profilePicture, tourist);
                return touristRepository.save(tourist);
            case HOST:
                Host host = new Host();
                registrationMapper.toEntity(dto, profilePicture, host);
                return hostRepository.save(host);
            default:
                throw new AuthException("Unsupported user type: " + dto.getUserType());
        }
    }

    @Override
    public NonadminResponseDTO loginNonadmin(UserLoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
            new AuthException("Невалидни корисничко име или лозинка.")
        );
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthException("Невалидни корисничко име или лозинка.");
        }
        if (!user.isNonadmin()) {
            throw new AuthException("Корисник није ни туриста ни власник.");
        }
        if (user.getStatus() != UserStatus.ACCEPTED) {
            throw new BadRequestException("Корисник је статуса: " + user.getStatus());
        }
        Nonadmin nonadmin = nonadminRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
            new AuthException("Невалидни корисничко име или лозинка.")
        );
        return nonadminMapper.toResDto(nonadmin);
    }

    @Override
    public void changePassword(PasswordChangeDTO dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(() ->
            new PasswordChangeException("Корисник чија се лозинка покушава променити, не постоји.")
        );
        if (user.getStatus() != UserStatus.ACCEPTED) {
            throw new PasswordChangeException("Промена лозинке није успела. (Регистрација није прихваћена)");
        }
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
