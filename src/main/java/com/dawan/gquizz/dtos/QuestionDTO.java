package com.dawan.gquizz.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class QuestionDTO {
    public String _id;

    public String question;

    public String answer;

    public List<String> badAnswers;

    public String category;

    public String difficulty;

    public List<String> allAnswers;
}
