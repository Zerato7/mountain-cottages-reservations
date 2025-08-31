package com.example.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.db.model.Cottage;
import com.example.backend.db.model.Host;
import com.example.backend.db.repository.CottageRepository;
import com.example.backend.db.repository.HostRepository;
import com.example.backend.dto.RequestDTO.CottageEditDTO;
import com.example.backend.dto.RequestDTO.CottageInsertDTO;
import com.example.backend.dto.ResponseDTO.CottageResponseDTO;
import com.example.backend.exception.BadRequestException;
import com.example.backend.exception.DuplicateDataException;
import com.example.backend.mapper.CottageMapper;
import com.example.backend.service.CottageService;

import jakarta.transaction.Transactional;

@Service
public class CottageServiceImpl implements CottageService {

    private final CottageMapper cottageMapper;
    
    private final CottageRepository cottageRepository;
    private final HostRepository hostRepository;

    public CottageServiceImpl(
        CottageMapper cottageMapper,
        CottageRepository cottageRepository,
        HostRepository hostRepository
    ) {
        this.cottageMapper = cottageMapper;
        this.cottageRepository = cottageRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public List<CottageResponseDTO> getAll() {
        return cottageRepository.findAll().stream()
            .map(cottageMapper::toResDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<CottageResponseDTO> getMy(Long id) {
        return cottageRepository.findByOwner_Id(id).stream()
            .map(cottageMapper::toResDto)
            .collect(Collectors.toList());
    }

    @Override
    public CottageResponseDTO getById(Long id) {
        Cottage cottage = cottageRepository.findById(id).orElseThrow(() ->
            new BadRequestException("Викендица под овим именом не постоји")
        );

        return cottageMapper.toResDto(cottage);
    }

    @Transactional
    @Override
    public CottageResponseDTO createCottage(CottageInsertDTO dto, List<MultipartFile> cottagePhotosFile) {
        if (cottageRepository.existsByName(dto.getName())) {
            throw new DuplicateDataException("Назив већ постоји", "name");
        }
        Host owner = hostRepository.findById(dto.getOwnerId()).orElseThrow(() ->
            new BadRequestException("Неадмин не постоји у систему.")
        );

        Cottage cottage = cottageMapper.toEntity(dto, owner, cottagePhotosFile);

        return cottageMapper.toResDto(cottageRepository.save(cottage));
    }

    @Transactional
    @Override
    public void deleteCottage(Long id) {
        Cottage cottage = cottageRepository.findById(id).orElseThrow(() ->
            new BadRequestException("Викендица не постоји.")
        );
        cottageRepository.delete(cottage);
    }

    @Override
    public CottageResponseDTO editCottage(CottageEditDTO dto, List<MultipartFile> cottagePhotosFile) {
        Cottage cottage = cottageRepository.findById(dto.getId()).orElseThrow(() ->
            new BadRequestException("Викендица не постоји.")
        ); 
        if (cottageRepository.existsByNameAndIdNot(dto.getName(), dto.getId())) {
            throw new DuplicateDataException("Назив већ постоји", "name");
        }

        cottageMapper.editFromDTO(cottage, dto, cottagePhotosFile);
        return cottageMapper.toResDto(cottageRepository.save(cottage));
    }

}
