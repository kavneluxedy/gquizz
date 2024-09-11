package com.dawan.gquizz.dtos;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Quiz {
    public String _id;

    public String question;

    public String answer;

    public List<String> badAnswers;

    public String category;

    public String difficulty;
}
