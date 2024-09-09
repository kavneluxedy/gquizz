package com.dawan.gquizz.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.Dictionary;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class User implements IUser {
    private String email;

    public String pseudo;

    private String password;

    public Dictionary<String, Integer> scores;

    @Override
    public int getScoreByCategory(String category) {
        return 0;
    }
}
