package com.rs.SB2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table( name = "student",
        uniqueConstraints=@UniqueConstraint(columnNames={"email"})
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    /**
     * Embedded will add all the fields of guardian class a fields in table of
     * this current entity, thus we have 2 classes but 1 table in the background.
     */
    @Embedded
    private Guardian guardian;
}
