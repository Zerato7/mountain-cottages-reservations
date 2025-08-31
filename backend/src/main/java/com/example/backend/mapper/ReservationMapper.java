package com.example.backend.mapper;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import com.example.backend.db.model.Cottage;
import com.example.backend.db.model.Feedback;
import com.example.backend.db.model.Reservation;
import com.example.backend.db.model.Tourist;
import com.example.backend.dto.RequestDTO.MakeReservationDTO;
import com.example.backend.dto.RequestDTO.RateReservationDTO;
import com.example.backend.dto.ResponseDTO.FeedbackResponseDTO;
import com.example.backend.dto.ResponseDTO.ReservationResponseDTO;
import com.example.backend.util.EncryptionUtil;

@Component
public class ReservationMapper {

    private final EncryptionUtil encryptionUtil;

    ReservationMapper(
        EncryptionUtil encryptionUtil
    ) {
        this.encryptionUtil = encryptionUtil;
    }
    
    public Reservation toEntity(MakeReservationDTO dto, Tourist tourist, Cottage cottage) {
        Reservation reservation = new Reservation();
        reservation.setTourist(tourist);
        reservation.setCottage(cottage);
        reservation.setDatetimeStart(dto.getStartDateTime());
        reservation.setDatetimeEnd(dto.getEndDateTime());
        reservation.setAdultsNumber(dto.getAdults());
        reservation.setChildrenNumber(dto.getChildren());
        reservation.setCost(dto.getCost());
        reservation.setSpecialDemands(dto.getSpecialDemands());

        if (dto.getCreditCardNumber() == null) {
            reservation.setCreditCardNumber(tourist.getCreditCardNumber());
        } else {
            reservation.setCreditCardNumber(encryptionUtil.encrypt(dto.getCreditCardNumber()));
        }
        
        reservation.setStatus(Reservation.Status.PENDING);
    
        return reservation;
    }

    public ReservationResponseDTO toResDto(Reservation reservation) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setId(reservation.getId());
        dto.setTouristFirstname(reservation.getTourist().getFirstName());
        dto.setTouristLastname(reservation.getTourist().getLastName());
        dto.setCottageName(reservation.getCottage().getName());
        dto.setCottageLocation(reservation.getCottage().getLocation());

        dto.setDatetimeStart(reservation.getDatetimeStart());
        dto.setDatetimeEnd(reservation.getDatetimeEnd());
        dto.setAdultsNumber(reservation.getAdultsNumber());
        dto.setChildrenNumber(reservation.getChildrenNumber());
        dto.setCost(reservation.getCost());
        dto.setSpecialDemands(reservation.getSpecialDemands());
        dto.setStatus(reservation.getStatus());
        dto.setRejectionComment(reservation.getRejectionComment());

        dto.setFeedback(this.toResDto(reservation.getFeedback()));

        return dto;
    }

    public Feedback toEntity(RateReservationDTO dto, Reservation reservation) {
        Feedback feedback = new Feedback();
        feedback.setReservation(reservation);
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());
        feedback.setDateTimeCreation(OffsetDateTime.now());

        reservation.setFeedback(feedback);

        return feedback;
    }

    public FeedbackResponseDTO toResDto(Feedback feedback) {
        if (feedback == null) return null;

        FeedbackResponseDTO dto = new FeedbackResponseDTO();
        dto.setReservationId(feedback.getReservation().getId());
        dto.setRating(feedback.getRating());
        dto.setComment(feedback.getComment());
        dto.setDateTimeCreation(feedback.getDateTimeCreation());

        return dto;
    }

}
