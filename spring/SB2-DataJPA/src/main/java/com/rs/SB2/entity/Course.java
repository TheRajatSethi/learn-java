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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // better to use sequences though...
    private Long courseId;
    private String title;
    private Integer credit;

    /**
     * You cannot define join column here. The join column and join relationship is
     * already defined in the CourseMaterial. We are only creating a bi-directional
     * reference here so that when we fetch course we also get course material
     * back. Thus instead of @JoinColumn we just reference @OneToOne with mappedBy.
     * This won't create a field of courseMaterial in course table.
     */
    @OneToOne(
            mappedBy = "course"
    )
    private CourseMaterial  courseMaterial;

    /**
     * ManyToOne is preferred as it mimics what the DB will do.
     * This will create a
     */
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "teacher_id",
            referencedColumnName = "teacherId"
    )
    private Teacher teacher;

    /**
     * ManyToMany will introduce a new table thus @JoinTable annotation instead of @JoinColumn
     */
    @ManyToMany
    @JoinTable(
            name = "student_course_mapping",
            joinColumns = @JoinColumn(
                    name = "course_id", // column name in student_course_mapping table.
                    referencedColumnName = "courseId"  // field name in Course class
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id", // column name in student_course_mapping table.
                    referencedColumnName = "Id" // field name in Student class
            )
    )
    private List<Student> students;
}
