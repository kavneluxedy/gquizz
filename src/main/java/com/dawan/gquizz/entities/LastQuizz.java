package com.dawan.gquizz.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "last_quizz")
public class LastQuizz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(length = Integer.MAX_VALUE, nullable = false)
    private List<String> idQuestions;

    @OneToOne
    @JoinColumn(name = "user_email", nullable = false)
    private User user;
}
