package com.example.backend.mapper;

import org.springframework.stereotype.Component;

import com.example.backend.db.model.Feedback;
import com.example.backend.db.model.Reservation;
import com.example.backend.dto.ResponseDTO.FeedbackResponseDTO;
import com.example.backend.dto.ResponseDTO.ReservationResponseDTO;

@Component
public class ReservationMapper {
    
    public ReservationResponseDTO toResDto(Reservation reservation) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setUsername(reservation.getTourist().getUsername());
        dto.setCottageName(reservation.getCottage().getName());

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

    public FeedbackResponseDTO toResDto(Feedback feedback) {
        if (feedback == null) return null;

        FeedbackResponseDTO dto = new FeedbackResponseDTO();
        dto.setRating(feedback.getRating());
        dto.setComment(feedback.getComment());

        return dto;
    }

}
