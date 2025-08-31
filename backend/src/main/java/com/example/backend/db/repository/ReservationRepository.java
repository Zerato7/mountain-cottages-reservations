package com.example.backend.db.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.db.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
            SELECT r 
            FROM Reservation r 
            WHERE r.cottage.id = :cottageId AND r.status = 'ACCEPTED_BY_OWNER' AND
            r.datetimeEnd > :datetimeStart AND r.datetimeStart < :datetimeEnd
            """)
    List<Reservation> findOverlapsCottage(@Param("cottageId") Long cottageId,
        @Param("datetimeStart") OffsetDateTime datetimeStart,
        @Param("datetimeEnd") OffsetDateTime datetimeEnd);

    @Query("""
            SELECT r
            FROM Reservation r
            WHERE r.tourist.id = :touristId AND r.status = 'ACCEPTED_BY_OWNER' AND
            r.datetimeEnd > :datetimeStart AND r.datetimeStart < :datetimeEnd
            """)
    List<Reservation> findOverlapsTourist(@Param("touristId") Long touristId,
        @Param("datetimeStart") OffsetDateTime datetimeStart,
        @Param("datetimeEnd") OffsetDateTime datetimeEnd);

    List<Reservation> findByTourist_Id(Long touristId);

    @Query("""
            SELECT r
            FROM Reservation r
            JOIN r.cottage c
            JOIN c.owner h
            WHERE h.id = :hostId
            """)
    List<Reservation> findByHost_Id(@Param("hostId") Long hostId);

    @Query("""
            SELECT COUNT(r)
            FROM Reservation r
            WHERE r.datetimeStart >= :dateTime1 AND r.datetimeEnd < :dateTime2
            AND r.status = 'ACCEPTED_BY_OWNER'
            """)
    Long countAfter(@Param("dateTime1") OffsetDateTime dateTime1, 
                @Param("dateTime2") OffsetDateTime dateTime2);
}
