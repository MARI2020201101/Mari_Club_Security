package com.mariworld.club.security.filter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ApiCheckFilter extends OncePerRequestFilter {

    private AntPathMatcher antPathMatcher;
    private String pattern;
    public ApiCheckFilter(String pattern){
        this.antPathMatcher = new AntPathMatcher();
        this.pattern=pattern;
    }
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("RequestURI: "+ request.getRequestURI());
        log.info(String.valueOf(antPathMatcher.match(pattern, request.getRequestURI())));

        if(antPathMatcher.match(pattern, request.getRequestURI())){
            log.info("ApiCheckFilter....................................");

            boolean checkHeader = checkAuthHeader(request);
            if(checkHeader){
                filterChain.doFilter(request,response);
                return;
            }else{
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "Fail check api header authorization";
                json.put("code","403");
                json.put("message",message);

                PrintWriter out = response.getWriter();
                out.print(json);
                return;

            }
        }
        filterChain.doFilter(request,response);
    }

    private boolean checkAuthHeader(HttpServletRequest request) {

        boolean checkResult = false;
        String authHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(authHeader)){
            log.info("authHeader : "+ authHeader);
            if(authHeader.equals("securityAuthHeader")){
                checkResult=true;
            }
        }
        return checkResult;
    }
}
