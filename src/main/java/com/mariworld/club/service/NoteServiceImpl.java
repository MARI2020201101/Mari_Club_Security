package com.mariworld.club.service;


import com.mariworld.club.dto.NoteDTO;
import com.mariworld.club.entity.Note;
import com.mariworld.club.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {
        return noteRepository.getList(writerEmail)
                .stream()
                .map(note -> entityToDTO(note))
                .collect(Collectors.toList());
    }

    @Override
    public Long register(NoteDTO noteDTO) {
        Note note = dtoToEntity(noteDTO);
        noteRepository.save(note);
        return note.getNum();
    }

    @Override
    public void modify(NoteDTO noteDTO) {
        Optional<Note> result = noteRepository.findById(noteDTO.getNum());
        if(result.isPresent()){
            Note note = result.get();
            note.changeContent(noteDTO.getContent());
            note.changeTitle(noteDTO.getTitle());
            noteRepository.save(note);
        }

    }

    @Override
    public void remove(Long num) {
        Optional<Note> result = noteRepository.findById(num);
        if(result.isPresent()){
            noteRepository.delete(result.get());
        }
    }

    @Override
    public NoteDTO get(Long num) {
        Optional<Note> result = noteRepository.getWithWriter(num);
        if(result.isPresent()){
            Note note = result.get();
            return entityToDTO(note);
        }
        return null;
    }
}
