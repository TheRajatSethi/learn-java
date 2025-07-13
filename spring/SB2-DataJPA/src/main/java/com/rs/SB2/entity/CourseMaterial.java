package com.rs.SB2.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long courseMaterialId;
    private String url;

    @OneToOne(
            // Changes you do on CourseMaterial will be cascaded to course as well.
            // E.g. if you save CourseMaterial it will save Course as well.
            cascade = CascadeType.ALL,
            // If lazy won't fetch course when you fetch course details.
            fetch = FetchType.EAGER,
            // If you set optional = False you will not be allowed to save a course without its material.
            optional = false
    )
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "courseId"
    )
    private Course course;
}
