package com.assignment.abcfitness.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assignment.abcfitness.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select count(*) from Booking b where b.createClassForClub.classId=:classId AND b.participationDate=:participationDate")
    Long getBookingCountByClassAndParticipationDate(@Param("classId") Long classId,
            @Param("participationDate") LocalDate participationDate);

}
