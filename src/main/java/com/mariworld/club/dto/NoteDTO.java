package com.mariworld.club.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {

    private Long num;
    private String title;
    private String content;
    private String writerEmail;
    private LocalDateTime modDate,regDate;


}
