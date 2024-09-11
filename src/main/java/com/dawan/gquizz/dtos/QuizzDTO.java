package com.dawan.gquizz.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class QuizzDTO {
    public int count;
    public Quiz[] quizzes;
}