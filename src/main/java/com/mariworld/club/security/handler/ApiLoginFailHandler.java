package com.mariworld.club.security.handler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ApiLoginFailHandler implements AuthenticationFailureHandler {
    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("ApiLoginFailHandler..........................");
        log.info(exception.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;utf-8");
        JSONObject json = new JSONObject();
        String message = "authentication failed!!";
        json.put("code","401");
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.print(json);
    }
}
