package com.dawan.gquizz.dtos;

import lombok.Data;

@Data
public class QuestionDTO {
    public String value;

    public String category;

    public String answer;

    public String[] badAnswers;
}
