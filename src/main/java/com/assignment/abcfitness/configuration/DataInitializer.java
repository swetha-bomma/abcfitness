package com.assignment.abcfitness.configuration;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.abcfitness.entity.Member;
import com.assignment.abcfitness.repository.MemberRepository;

@Configuration
public class DataInitializer {

    private final MemberRepository memberRepository;

    public DataInitializer(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    @Transactional
    public ApplicationRunner initializeData() {
        return args -> {
            List<Member> members = List.of(
                    Member.builder().memberName("Member One").build(),
                    Member.builder().memberName("Member Two").build(),
                    Member.builder().memberName("Member Three").build());

            memberRepository.saveAll(members);

        };
    }

}
