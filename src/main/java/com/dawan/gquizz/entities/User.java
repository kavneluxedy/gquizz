package com.dawan.gquizz.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User implements Serializable {
	@Id
    @Column(unique = true, nullable = false)
    private String email;

    private String pseudo;
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Score> scores;

    @OneToOne(mappedBy = "user")
    private LastQuizz lastQuizz;
    
    @Column(name = "current_score")
    private int currentScore;
}
