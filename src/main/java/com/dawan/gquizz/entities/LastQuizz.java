package com.dawan.gquizz.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "last_quizz")
public class LastQuizz extends BaseEntity{

	private static final long serialVersionUID = 3470336777702835412L;

    @ElementCollection
    private List<String> idQuestions;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    private Category category;

    private int currentCount;

    private int currentScore;
}
