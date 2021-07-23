package com.mariworld.club.controller;

import com.mariworld.club.dto.NoteDTO;
import com.mariworld.club.service.NoteService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/new")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO) throws Exception{
        Long num = noteService.register(noteDTO);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping("/{num}")
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num){
        return new ResponseEntity<>(noteService.get(num),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoteDTO>> getList(String email)throws Exception{

        return new ResponseEntity<>(noteService.getAllWithWriter(email),HttpStatus.OK);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<String> remove(@PathVariable Long num)throws Exception{

        noteService.remove(num);
        return new ResponseEntity<>("removed",HttpStatus.OK);
    }

    @PutMapping("/{num}")
    public ResponseEntity<String> modify(@RequestBody NoteDTO noteDTO)throws Exception{

        noteService.modify(noteDTO);
        return new ResponseEntity<>("modified",HttpStatus.OK);
    }
}
