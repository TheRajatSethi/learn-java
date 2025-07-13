package com.rs.SB2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teacherId;

    private String name;

    /**
     * Commenting below out as defining @ManyToOne in the course table instead of defining
     * @OneToMany in the Teacher table.
     */
    /**
     * This will create a teacher_id column in the course table to manage OneToMany relationship.
     * No separate table will be created.
     */
//    @OneToMany(
//            cascade = CascadeType.ALL
//    )
//    @JoinColumn(
//        name = "teacher_id",
//        referencedColumnName = "teacherId"
//    )
//    private List<Course> courses;
}
