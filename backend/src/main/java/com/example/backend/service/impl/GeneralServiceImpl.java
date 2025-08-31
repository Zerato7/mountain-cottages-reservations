package com.example.backend.service.impl;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.example.backend.db.repository.CottageRepository;
import com.example.backend.db.repository.HostRepository;
import com.example.backend.db.repository.ReservationRepository;
import com.example.backend.db.repository.TouristRepository;
import com.example.backend.dto.ResponseDTO.GeneralInfoDTO;
import com.example.backend.service.GeneralService;

@Service
public class GeneralServiceImpl implements GeneralService {

    private final CottageRepository cottageRepository;
    private final HostRepository hostRepository;
    private final TouristRepository touristRepository;
    private final ReservationRepository reservationRepository;

    GeneralServiceImpl(
        CottageRepository cottageRepository,
        HostRepository hostRepository,
        TouristRepository touristRepository,
        ReservationRepository reservationRepository
    ) {
        this.cottageRepository = cottageRepository;
        this.hostRepository = hostRepository;
        this.touristRepository = touristRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public GeneralInfoDTO getInfo() {
        GeneralInfoDTO dto = new GeneralInfoDTO();
        dto.setCottageNum(cottageRepository.count());
        dto.setHostNum(hostRepository.count());
        dto.setTouristNum(touristRepository.count());
        dto.setReservation24HNum(reservationRepository.countAfter(OffsetDateTime.now().minusHours(24), OffsetDateTime.now()));
        dto.setReservation7DNum(reservationRepository.countAfter(OffsetDateTime.now().minusDays(7), OffsetDateTime.now()));
        dto.setReservation30DNum(reservationRepository.countAfter(OffsetDateTime.now().minusDays(30), OffsetDateTime.now()));
        return dto;
    }
    
}
