package com.dawan.gquizz.dtos;

import com.dawan.gquizz.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScoreDTO {

    private Category category;

    private int bestScore;
}
