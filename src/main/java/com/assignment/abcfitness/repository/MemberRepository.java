package com.assignment.abcfitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.abcfitness.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
