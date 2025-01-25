package com.assignment.abcfitness.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assignment.abcfitness.entity.GymClass;

@Repository
public interface GymClassRepository extends JpaRepository<GymClass, Long> {

    @Query("select case when count(c) > 0 then true else false end " +
            "from GymClass c " +
            "where (c.startDate BETWEEN :startDate AND :endDate OR c.endDate BETWEEN :startDate AND :endDate) and c.club.id=:clubId")
    boolean isClassExistsBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
            @Param("clubId") Long clubId);

}
