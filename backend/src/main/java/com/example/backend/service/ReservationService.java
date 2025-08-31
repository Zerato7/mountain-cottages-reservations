package com.example.backend.service;

import java.util.List;

import com.example.backend.dto.RequestDTO.MakeReservationDTO;
import com.example.backend.dto.RequestDTO.RateReservationDTO;
import com.example.backend.dto.RequestDTO.ReservationStatusUpdateDTO;
import com.example.backend.dto.ResponseDTO.ReservationResponseDTO;

public interface ReservationService {

    ReservationResponseDTO createReservation(MakeReservationDTO dto);

    List<ReservationResponseDTO> getByTourist(Long id);

    List<ReservationResponseDTO> getByHost(Long id);

    ReservationResponseDTO cancel(ReservationStatusUpdateDTO dto);

    ReservationResponseDTO accept(ReservationStatusUpdateDTO dto);

    ReservationResponseDTO deny(ReservationStatusUpdateDTO dto);

    ReservationResponseDTO rate(RateReservationDTO dto);
    
}
