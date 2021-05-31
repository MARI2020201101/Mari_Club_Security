package com.mariworld.club.security.service;

import com.mariworld.club.entity.ClubMember;
import com.mariworld.club.security.dto.ClubMemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClubMemberDetailsServiceTest {

    @Autowired
    private ClubMemberDetailsService service;

    @Test
    public void loadUserTest(){
        ClubMemberDTO clubMember  = (ClubMemberDTO) service.loadUserByUsername("user1@aaa.com");
        System.out.println(clubMember);
    }
}
