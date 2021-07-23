package com.mariworld.club.security.handler;

import com.mariworld.club.dto.ClubMemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    private PasswordEncoder passwordEncoder;
    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        log.info("--------------------------------------------------------");
        log.info("\nClubLoginSuccessHandler\n");

        ClubMemberDTO clubMemberDTO = (ClubMemberDTO) authentication.getPrincipal();
        Boolean fromSocial = clubMemberDTO.isFromSocial();
        Boolean passwordResult = passwordEncoder.matches("1111", clubMemberDTO.getPassword());
        if(fromSocial && passwordResult){
            redirectStrategy.sendRedirect(request,response,"/member/modify?from=social");
        }
    }
}
