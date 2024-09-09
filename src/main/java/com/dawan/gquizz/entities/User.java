package com.dawan.gquizz.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "users")
public class User implements IUser {
    private String email;

    public String pseudo;

    private String password;

    public Set<Integer> scores;

    @Override
    public int getScoreByCategory(String category) {
        return 0;
    }
}
