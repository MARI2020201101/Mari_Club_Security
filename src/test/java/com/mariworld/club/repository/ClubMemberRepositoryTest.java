package com.mariworld.club.repository;

import com.mariworld.club.entity.ClubMember;
import com.mariworld.club.entity.ClubMemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberRepositoryTest {

    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1,100).forEach(i->{
            ClubMember clubMember = ClubMember.builder()
                    .email("user"+i+"@aaa.com")
                    .name("사용자"+i)
                    .password(passwordEncoder.encode("1111"))
                    .fromSocial(false)
                    .build();
            if(i>90){
                clubMember.addClubMemberRole(ClubMemberRole.ADMIN);
            }
            if(i>80){
                clubMember.addClubMemberRole(ClubMemberRole.MANAGER);
            }

                clubMember.addClubMemberRole(ClubMemberRole.USER);
            clubMemberRepository.save(clubMember);
        });

    }

    @Test
    public void findByEmailTest(){
        ClubMember clubMember = clubMemberRepository.findByEmail("user91@aaa.com",false)
                .get();
        System.out.println(clubMember);
        clubMember.getRoleSet().stream().forEach(System.out::println);
        System.out.println();
        clubMember.getRoleSet().stream().forEach(r->System.out.println(r.name()));
    }
}
