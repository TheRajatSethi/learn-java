package com.rs.SB2.repository;

import com.rs.SB2.entity.Guardian;
import com.rs.SB2.entity.Student;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
//@SpringBootTest
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudent(){
        // Given
        String name = "Sam Nelson";
        String email = "sam.nelson@gmail.com";

        Student student = Student.builder()
                .name(name)
                .email(email)
                .build();

        // Act
        studentRepository.save(student);
        Long id = student.getId();
        Student savedStudent = studentRepository.getReferenceById(id);

        // Validate
        /**
         * Using assertJ for assertions and validations.
         */
        assertThat(savedStudent.getEmail()).isEqualTo(email);
        assertThat(savedStudent.getName()).isEqualTo(name);
    }

    @Test
    public void saveStudentWithGuardian(){
        // Given
        String name = "Sam Nelson";
        String email = "sam.nelson@gmail.com";
        String guardianName = "Pete Nelson";
        String guardianEmail = "pete.nelson@gmail.com";

        Guardian guardian = new Guardian(guardianName, guardianEmail);
        Student student = Student.builder()
                .name(name)
                .email(email)
                .guardian(guardian)
                .build();

        // Act
        studentRepository.save(student);
        Long id = student.getId();
        Student savedStudent = studentRepository.getReferenceById(id);

        // Validate
        /**
         * Using assertJ for assertions and validations.
         */
        assertThat(savedStudent.getEmail()).isEqualTo(email);
        assertThat(savedStudent.getName()).isEqualTo(name);
        assertThat(savedStudent.getGuardian().getName()).isEqualTo(guardianName);
        assertThat(savedStudent.getGuardian().getEmail()).isEqualTo(guardianEmail);
    }

    @Test
    public void findStudentWithGuardian(){
        // Given
        String name = "Sam Nelson";
        String email = "sam.nelson@gmail.com";
        String guardianName = "Pete Nelson";
        String guardianEmail = "pete.nelson@gmail.com";

        Guardian guardian = new Guardian(guardianName, guardianEmail);
        Student student = Student.builder()
                .name(name)
                .email(email)
                .guardian(guardian)
                .build();

        // Act
        studentRepository.save(student);
        Long id = student.getId();
        Student savedStudent = studentRepository.findByGuardianName(guardianName).get(0);

        // Validate
        /**
         * Using assertJ for assertions and validations.
         */
        assertThat(savedStudent.getEmail()).isEqualTo(email);
        assertThat(savedStudent.getName()).isEqualTo(name);
        assertThat(savedStudent.getGuardian().getName()).isEqualTo(guardianName);
        assertThat(savedStudent.getGuardian().getEmail()).isEqualTo(guardianEmail);
    }

    @Test
    public void findStudentByFirstName_JPQLQueryMethod(){
        // Given
        String name = "Sam Nelson";
        String email = "sam.nelson@gmail.com";
        String guardianName = "Pete Nelson";
        String guardianEmail = "pete.nelson@gmail.com";

        Guardian guardian = new Guardian(guardianName, guardianEmail);
        Student student = Student.builder()
                .name(name)
                .email(email)
                .guardian(guardian)
                .build();

        // Act
        studentRepository.save(student);
        Long id = student.getId();
        Student savedStudent = studentRepository.getStudentJPQLQuery(name);

        // Validate
        /**
         * Using assertJ for assertions and validations.
         */
        assertThat(savedStudent.getEmail()).isEqualTo(email);
        assertThat(savedStudent.getName()).isEqualTo(name);
        assertThat(savedStudent.getGuardian().getName()).isEqualTo(guardianName);
        assertThat(savedStudent.getGuardian().getEmail()).isEqualTo(guardianEmail);
    }

    @Test
    public void findStudentByFirstName_NativeQueryMethod(){
        // Given
        String name = "Sam Nelson";
        String email = "sam.nelson@gmail.com";
        String guardianName = "Pete Nelson";
        String guardianEmail = "pete.nelson@gmail.com";

        Guardian guardian = new Guardian(guardianName, guardianEmail);
        Student student = Student.builder()
                .name(name)
                .email(email)
                .guardian(guardian)
                .build();

        // Act
        studentRepository.save(student);
        Long id = student.getId();
        Student savedStudent = studentRepository.getStudentNativeQuery(name);

        // Validate
        /**
         * Using assertJ for assertions and validations.
         */
        assertThat(savedStudent.getEmail()).isEqualTo(email);
        assertThat(savedStudent.getName()).isEqualTo(name);
        assertThat(savedStudent.getGuardian().getName()).isEqualTo(guardianName);
        assertThat(savedStudent.getGuardian().getEmail()).isEqualTo(guardianEmail);
    }
}
