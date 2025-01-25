package com.assignment.abcfitness.configuration;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.abcfitness.entity.Gym;
import com.assignment.abcfitness.entity.Member;
import com.assignment.abcfitness.entity.Owner;
import com.assignment.abcfitness.repository.GymRepository;
import com.assignment.abcfitness.repository.MemberRepository;
import com.assignment.abcfitness.repository.OwnerRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberRepository memberRepository;
    private final GymRepository gymRepository;
    private final OwnerRepository ownerRepository;

    @Bean
    @Transactional
    public ApplicationRunner initializeData() {
        return args -> {
            List<Member> members = List.of(
                    Member.builder().memberName("Member One").build(),
                    Member.builder().memberName("Member Two").build(),
                    Member.builder().memberName("Member Three").build());

            memberRepository.saveAll(members);

            List<Owner> owners = List.of(
                    Owner.builder().name("Owner One").build(),
                    Owner.builder().name("Owner Two").build(),
                    Owner.builder().name("Owner Three").build(),
                    Owner.builder().name("Owner Four").build());

            ownerRepository.saveAll(owners);

            List<Gym> gyms = List.of(
                    Gym.builder().name("Gym One").build(),
                    Gym.builder().name("Gym Two").build(),
                    Gym.builder().name("Gym Three").build(),
                    Gym.builder().name("Gym Four").build());

            gymRepository.saveAll(gyms);

        };
    }

}
