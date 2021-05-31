package com.mariworld.club.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public void member(){
        log.info("for member...................");
    }

    @GetMapping("/admin")
    public void admin(){
        log.info("for admin...................");
    }
}
