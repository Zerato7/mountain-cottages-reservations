package com.example.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.db.model.Cottage;
import com.example.backend.db.model.Reservation;
import com.example.backend.db.model.Tourist;
import com.example.backend.db.repository.CottageRepository;
import com.example.backend.db.repository.ReservationRepository;
import com.example.backend.db.repository.TouristRepository;
import com.example.backend.dto.RequestDTO.MakeReservationDTO;
import com.example.backend.dto.RequestDTO.RateReservationDTO;
import com.example.backend.dto.RequestDTO.ReservationStatusUpdateDTO;
import com.example.backend.dto.ResponseDTO.ReservationResponseDTO;
import com.example.backend.exception.BadRequestException;
import com.example.backend.mapper.ReservationMapper;
import com.example.backend.service.ReservationService;

import jakarta.transaction.Transactional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;

    private final TouristRepository touristRepository;
    private final CottageRepository cottageRepository;
    private final ReservationRepository reservationRepository;

    ReservationServiceImpl(
        ReservationMapper reservationMapper,
        TouristRepository touristRepository,
        CottageRepository cottageRepository,
        ReservationRepository reservationRepository
    ) {
        this.reservationMapper = reservationMapper;
        this.touristRepository = touristRepository;
        this.cottageRepository = cottageRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationResponseDTO createReservation(MakeReservationDTO dto) {
        Tourist tourist = touristRepository.findById(dto.getTouristId()).orElseThrow(() ->
            new BadRequestException("Туриста не постоји.")
        );
        Cottage cottage = cottageRepository.findById(dto.getCottageId()).orElseThrow(() ->
            new BadRequestException("Викендица не постоји.")
        );
        if (cottage.getDateTimeTilBlocked() != null && 
            !cottage.getDateTimeTilBlocked().isBefore(dto.getStartDateTime())) {
            throw new BadRequestException("Викендица је блокирана.");
        }
        List<Reservation> overlappingReservationsCottage = reservationRepository
            .findOverlapsCottage(cottage.getId(), dto.getStartDateTime(), dto.getEndDateTime());
        if (overlappingReservationsCottage.size() > 0) {
            throw new BadRequestException("Викендица није слободна.");
        }
        List<Reservation> overlappingReservationsTourist = reservationRepository
            .findOverlapsTourist(tourist.getId(), dto.getStartDateTime(), dto.getEndDateTime());
        if (overlappingReservationsTourist.size() > 0) {
            throw new BadRequestException("Заузет си у овом термину.");
        }

        if (cottage.getCapacity() < dto.getAdults() + dto.getChildren()) {
            throw new BadRequestException("Нема довољно места у викендици.");
        }

        Reservation reservation = reservationMapper.toEntity(dto, tourist, cottage);
        reservationRepository.save(reservation);
        return reservationMapper.toResDto(reservation);
    }

    @Override
    public List<ReservationResponseDTO> getByTourist(Long id) {
        return reservationRepository.findByTourist_Id(id).stream()
            .map(reservationMapper::toResDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDTO> getByHost(Long id) {
        return reservationRepository.findByHost_Id(id).stream()
            .map(reservationMapper::toResDto)
            .collect(Collectors.toList());
    }

    @Override
    public ReservationResponseDTO cancel(ReservationStatusUpdateDTO dto) {
        Reservation reservation = reservationRepository.findById(dto.getId()).orElseThrow(() ->
            new BadRequestException("Резервација не постоји.")
        );
        reservation.setStatus(Reservation.Status.CANCELLED_BY_TOURIST);
        return reservationMapper.toResDto(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponseDTO accept(ReservationStatusUpdateDTO dto) {
        Reservation reservation = reservationRepository.findById(dto.getId()).orElseThrow(() ->
            new BadRequestException("Резервација не постоји.")
        );
        if (reservation.getStatus() != Reservation.Status.PENDING) {
            throw new BadRequestException("Захтев за резервацијом није на чекању већ је " +
            reservation.getStatus());
        }
        List<Reservation> overlappingReservationsCottage = reservationRepository
            .findOverlapsCottage(reservation.getCottage().getId(), 
                        reservation.getDatetimeStart(), 
                        reservation.getDatetimeEnd());
        if (overlappingReservationsCottage.size() > 0) {
            throw new BadRequestException("Викендица није слободна.");
        }
        List<Reservation> overlappingReservationsTourist = reservationRepository
            .findOverlapsTourist(reservation.getTourist().getId(), 
                        reservation.getDatetimeStart(), 
                        reservation.getDatetimeEnd());
        if (overlappingReservationsTourist.size() > 0) {
            throw new BadRequestException("Туриста је заузет у овом термину.");
        }
        
        reservation.setStatus(Reservation.Status.ACCEPTED_BY_OWNER);
        return reservationMapper.toResDto(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponseDTO deny(ReservationStatusUpdateDTO dto) {
        Reservation reservation = reservationRepository.findById(dto.getId()).orElseThrow(() ->
            new BadRequestException("Резервација не постоји.")
        );
        if (reservation.getStatus() != Reservation.Status.PENDING) {
            throw new BadRequestException("Захтев за резервацијом није на чекању већ је " +
            reservation.getStatus());
        }

        reservation.setStatus(Reservation.Status.REJECTED_BY_OWNER);
        reservation.setRejectionComment(dto.getRejectionComment());
        return reservationMapper.toResDto(reservationRepository.save(reservation));
    }

    @Transactional
    @Override
    public ReservationResponseDTO rate(RateReservationDTO dto) {
        Reservation reservation = reservationRepository.findById(dto.getReservationId()).orElseThrow(() ->
            new BadRequestException("Резервација не постоји.")
        );

        reservationMapper.toEntity(dto, reservation);
        reservationRepository.save(reservation);
        return reservationMapper.toResDto(reservation);
    }
    
}
