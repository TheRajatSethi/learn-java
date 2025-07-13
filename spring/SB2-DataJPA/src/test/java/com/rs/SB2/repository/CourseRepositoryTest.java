package com.rs.SB2.repository;

import com.rs.SB2.entity.Course;
import com.rs.SB2.entity.CourseMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Test
    void saveCourseAndMaterial() {
        Course math = Course.builder()
                .title("math")
                .credit(6)
                .build();

        CourseMaterial mathMaterial = CourseMaterial.builder()
                .url("google.com")
                .course(math)
                .build();

        courseMaterialRepository.save(mathMaterial);
    }

}