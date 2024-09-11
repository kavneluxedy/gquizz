package com.dawan.gquizz.dtos;

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

    public String[] badAnswers;

    public String category;

    public String difficulty;
}
