package com.mariworld.club.security.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

public class JWTUtilTest {

    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefore(){
        jwtUtil = new JWTUtil();

    }
    @Test
    public void encodeTest() throws Exception {
        String email = "user11@aaa.com";
        String str = jwtUtil.generateToken(email);
        System.out.println(str);
        Thread.sleep(1001);
        String result = jwtUtil.validateAndExtract(str);
        System.out.println(result);
    }
}
