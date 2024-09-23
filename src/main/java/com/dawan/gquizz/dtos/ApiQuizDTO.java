package com.dawan.gquizz.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiQuizDTO {
    private QuestionDTO quiz;
}