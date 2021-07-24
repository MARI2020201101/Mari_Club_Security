package com.mariworld.club.security.filter;

import com.mariworld.club.dto.ClubMemberDTO;
import com.mariworld.club.entity.ClubMember;
import com.mariworld.club.security.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JWTUtil jwtUtil;
    public ApiLoginFilter(String defaultFilterProcessesUrl , JWTUtil jwtUtil) {

        super(defaultFilterProcessesUrl);
        this.jwtUtil=jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("ApiLoginFilter.......................");
        String email=request.getParameter("email");
        String pw=request.getParameter("pw");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,pw);
        if(email==null){
            throw new BadCredentialsException("email cannot be null");
        }

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("authResult : "+authResult.getPrincipal());

        super.successfulAuthentication(request, response, chain, authResult);
        String email = ((ClubMemberDTO)authResult.getPrincipal()).getEmail();
        log.info(email);
        String token =null;
        try {
            token = jwtUtil.generateToken(email);
            response.setContentType("text/plain");
            response.getOutputStream().write(token.getBytes(StandardCharsets.UTF_8));
            log.info(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
