package com.dawan.gquizz.dtos;

import com.dawan.gquizz.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScoreDTO {
    private String userPseudo;
    private String categoryLabel;
    private int bestScore;
}
