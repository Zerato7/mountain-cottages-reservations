package com.example.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.db.model.Nonadmin;
import com.example.backend.db.repository.NonadminRepository;
import com.example.backend.db.repository.UserRepository;
import com.example.backend.dto.RequestDTO.UserEditDTO;
import com.example.backend.dto.ResponseDTO.NonadminResponseDTO;
import com.example.backend.exception.BadRequestException;
import com.example.backend.exception.DuplicateUserException;
import com.example.backend.mapper.NonadminMapper;
import com.example.backend.service.NonadminService;

@Service
public class NonadminServiceImpl implements NonadminService {

    private final NonadminMapper nonadminMapper;

    private final NonadminRepository nonadminRepository;
    private final UserRepository userRepository;

    public NonadminServiceImpl(
        NonadminMapper nonadminMapper,
        NonadminRepository nonadminRepository,
        UserRepository userRepository
    ) {
        this.nonadminMapper = nonadminMapper;
        this.nonadminRepository = nonadminRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<NonadminResponseDTO> getAll() {
        return nonadminRepository.findAll().stream()
            .map(nonadminMapper::toResDto)
            .collect(Collectors.toList());
    }

    @Override
    public void editNonadmin(UserEditDTO dto, MultipartFile profilePicture) {
        Nonadmin nonadmin = nonadminRepository.findById(dto.getId()).orElseThrow(() ->
            new BadRequestException("Неадмин не постоји у систему.")
        );
        if (userRepository.existsByUsernameAndIdNot(dto.getUsername(), dto.getId())) {
            throw new DuplicateUserException("Корисничко име већ постоји.", "username");
        }
        if (nonadminRepository.existsByEmailAndIdNot(dto.getEmail(), dto.getId())) {
            throw new DuplicateUserException("Е-мејл адреса већ постоји.", "email");
        }
        nonadminMapper.editFromDto(nonadmin, dto, profilePicture);
        nonadminRepository.save(nonadmin);
    }
    
}
