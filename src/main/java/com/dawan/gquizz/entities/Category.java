package com.dawan.gquizz.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -3935886453200353255L;

    @Column(unique = true, nullable = false)
    private String label;
}
