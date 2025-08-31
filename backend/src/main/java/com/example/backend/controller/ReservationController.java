package com.example.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.RequestDTO.MakeReservationDTO;
import com.example.backend.dto.RequestDTO.RateReservationDTO;
import com.example.backend.dto.RequestDTO.ReservationStatusUpdateDTO;
import com.example.backend.dto.ResponseDTO.ReservationResponseDTO;
import com.example.backend.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
    
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationResponseDTO> createReservation(@Valid @RequestBody MakeReservationDTO dto) {
        return ResponseEntity.ok(reservationService.createReservation(dto));
    }

    @GetMapping("/getByTourist/{id}")
    public ResponseEntity<List<ReservationResponseDTO> > getByTourist(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getByTourist(id));
    }

    @GetMapping("/getByHost/{id}")
    public ResponseEntity<List<ReservationResponseDTO> > getByHost(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getByHost(id));
    }

    @PutMapping("/cancel")
    public ResponseEntity<ReservationResponseDTO> cancel(@Valid @RequestBody ReservationStatusUpdateDTO dto) {
        return ResponseEntity.ok(reservationService.cancel(dto));
    }

    @PutMapping("/accept")
    public ResponseEntity<ReservationResponseDTO> accept(@Valid @RequestBody ReservationStatusUpdateDTO dto) {
        return ResponseEntity.ok(reservationService.accept(dto));
    }

    @PutMapping("/reject")
    public ResponseEntity<ReservationResponseDTO> deny(@Valid @RequestBody ReservationStatusUpdateDTO dto) {
        return ResponseEntity.ok(reservationService.deny(dto));
    }

    @PostMapping("/rate")
    public ResponseEntity<ReservationResponseDTO> rate(@Valid @RequestBody RateReservationDTO dto) {
        return ResponseEntity.ok(reservationService.rate(dto));
    }

}
