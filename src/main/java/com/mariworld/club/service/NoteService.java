package com.mariworld.club.service;

import com.mariworld.club.dto.NoteDTO;
import com.mariworld.club.entity.ClubMember;
import com.mariworld.club.entity.Note;
import org.springframework.stereotype.Service;

import java.util.List;


public interface NoteService {

    List<NoteDTO> getAllWithWriter(String writerEmail);
    Long register(NoteDTO noteDTO);
    void modify(NoteDTO noteDTO);
    void remove(Long num);
    NoteDTO get(Long num);


    default NoteDTO entityToDTO(Note note){

        NoteDTO noteDTO = NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .writerEmail(note.getWriter().getEmail())
                .content(note.getContent())
                .modDate(note.getModDate())
                .regDate(note.getRegDate())
                .build();
        return noteDTO;
    };
    default Note dtoToEntity(NoteDTO noteDTO){
        Note note = Note.builder()
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .writer(ClubMember.builder().email(noteDTO.getWriterEmail()).build())
                .build();
        return note;
    };
}
