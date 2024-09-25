package com.dawan.gquizz.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true) // on all entities
@Entity
@Table(name = "scores")
public class Score extends BaseEntity {

	private static final long serialVersionUID = 3739609421340440851L;

	//@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email", nullable = false)
    private User user;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int bestScore;
}
