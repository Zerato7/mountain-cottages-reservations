package com.example.backend.mapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.db.model.Cottage;
import com.example.backend.db.model.CottagePhoto;
import com.example.backend.db.model.Host;
import com.example.backend.dto.RequestDTO.CottageInsertDTO;
import com.example.backend.dto.ResponseDTO.CottagePhotoResponseDTO;
import com.example.backend.dto.ResponseDTO.CottageResponseDTO;
import com.example.backend.exception.BackendServerException;
import com.example.backend.util.ImageUtil;

@Component
public class CottageMapper {
    
    private final ImageUtil imageUtil;
    
    private final ReservationMapper reservationMapper;

    public CottageMapper(
        ImageUtil imageUtil,
        ReservationMapper reservationMapper
    ) {
        this.imageUtil = imageUtil;
        this.reservationMapper = reservationMapper;
    }

    public Cottage toEntity(CottageInsertDTO dto, Host owner, List<MultipartFile> cottagePhotosFile) {
        Cottage cottage = new Cottage();
        cottage.setName(dto.getName());
        cottage.setLocation(dto.getLocation());
        cottage.setCapacity(dto.getCapacity());
        cottage.setServices(dto.getServices());
        cottage.setWinterPriceAdult(dto.getWinterPriceAdult());
        cottage.setWinterPriceChild(dto.getWinterPriceChild());
        cottage.setSummerPriceAdult(dto.getSummerPriceAdult());
        cottage.setSummerPriceChild(dto.getSummerPriceChild());
        cottage.setPhoneNumber(dto.getPhoneNumber());
        cottage.setLatitude(dto.getLatitude());
        cottage.setLongitude(dto.getLongitude());
        cottage.setOwner(owner);
        try {
            cottage.setPhotosFolderPath(imageUtil.createFolder(
                owner.getUsername() + "_" + 
                dto.getName().replace(' ', '_') + "_" + 
                dto.getLocation().replace(' ', '_')
            ));
        } catch (IOException e) {
            throw new BackendServerException("Креирање директоријума није успело.");
        }
        
        if (cottagePhotosFile != null) {
            int cnt = 0;
            for (MultipartFile cottagePhotoFile : cottagePhotosFile) {
                try {
                    String cottagePhotoPath = imageUtil.saveImageToFileSys(
                        String.valueOf(cnt), 
                        cottage.getPhotosFolderPath(), 
                        cottagePhotoFile);
                    CottagePhoto cottagePhoto = new CottagePhoto();
                    cottagePhoto.setPhotoPath(cottagePhotoPath);
                    cottagePhoto.setPosition(cnt++);
                    cottagePhoto.setCottage(cottage);

                    cottage.getPhotos().add(cottagePhoto);
                } catch (IOException e) {
                    throw new BackendServerException("Уписивање слике није успело.");
                }
            }
        }

        return cottage;
    }
    
    public CottageResponseDTO toResDto(Cottage cottage) {
        CottageResponseDTO dto = new CottageResponseDTO();
        dto.setId(cottage.getId());
        dto.setName(cottage.getName());
        dto.setLocation(cottage.getLocation());
        dto.setCapacity(cottage.getCapacity());
        dto.setServices(cottage.getServices());
        dto.setWinterPriceAdult(cottage.getWinterPriceAdult());
        dto.setWinterPriceChild(cottage.getWinterPriceChild());
        dto.setSummerPriceAdult(cottage.getSummerPriceAdult());
        dto.setSummerPriceChild(cottage.getSummerPriceChild());
        dto.setPhoneNumber(cottage.getPhoneNumber());
        dto.setLatitude(cottage.getLatitude());
        dto.setLongitude(cottage.getLongitude());
        dto.setOwnerId(cottage.getOwner().getId());
        
        dto.setPhotoPaths(
            cottage.getPhotos().stream()
                .map(this::toResDto)
                .collect(Collectors.toList())
        );

        dto.setReservations(
            cottage.getReservations().stream()
                .map(reservationMapper::toResDto)
                .collect(Collectors.toList())
        );

        return dto;
    }

    public CottagePhotoResponseDTO toResDto(CottagePhoto cottagePhoto) {
        CottagePhotoResponseDTO dto = new CottagePhotoResponseDTO();
        dto.setPosition(cottagePhoto.getPosition());
        dto.setPhotoPath(cottagePhoto.getPhotoPath());

        return dto;
    }

}
