package com.mariworld.club.controller;

import com.mariworld.club.dto.ClubMemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/all")
    public void all(){
        log.info("for All...................");
    }

    @GetMapping("/member")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void member(@AuthenticationPrincipal ClubMemberDTO dto){
        log.info("for member...................");
        log.info("ClubMemberDTO : \n" +dto);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public void admin(){
        log.info("for admin...................");
    }

    @GetMapping("/exOnly")
    @PreAuthorize("#clubMemberDTO!= null && #clubMemberDTO.username eq 'user11@aaa.com'")
    public String exOnly(@AuthenticationPrincipal ClubMemberDTO clubMemberDTO){
        log.info("for exMemberOnly...................");
        return "/sample/member";
    }
}
