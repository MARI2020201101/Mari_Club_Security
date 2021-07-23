package com.mariworld.club.repository;

import com.mariworld.club.entity.ClubMember;
import com.mariworld.club.entity.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.stream.LongStream;

@SpringBootTest
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Test
    public void insertTest(){

        LongStream.rangeClosed(10,20).forEach(i-> {
            Note note = Note.builder()
                    .num(i)
                    .title("note" + i)
                    .content("content..." + i)
                    .writer(ClubMember.builder().email("user10@aaa.com").build()).build();

            noteRepository.save(note);
        });
    }

    @Test @Transactional
    public void getListTest(){
        noteRepository.getList("user10@aaa.com").forEach(System.out::println);

    }

}

