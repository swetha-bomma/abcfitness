package com.assignment.abcfitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.abcfitness.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
