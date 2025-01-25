package com.assignment.abcfitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.abcfitness.entity.Gym;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

}
