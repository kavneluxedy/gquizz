package com.dawan.gquizz.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    private String email;

    public String pseudo;

    private String password;

    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Integer> scores;
}
