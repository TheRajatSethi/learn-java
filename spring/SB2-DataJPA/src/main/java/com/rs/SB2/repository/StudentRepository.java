package com.rs.SB2.repository;

import com.rs.SB2.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Jpa repository brings the functionality of Simple CRUD repository and PagingAndSortingRepository.
 * If you do not need the functionality of the JPA repository use the CRUD repository so that you are
 * not tied down to the JPA repository functionality.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public List<Student> findByGuardianName(String guardianName);

    /**
     * JQPL Query
     */
    @Query("select s from Student s where s.name = ?1")
    Student getStudentJPQLQuery(String name);

    /**
     * Native Query
     */
    @Query(
            value = "SELECT * from student WHERE name = ?1",
            nativeQuery = true
    )
    Student getStudentNativeQuery(String name);

    /**
     * Modifying data.
     */
    @Modifying
    @Transactional  // Required if you directly use repository methods else this will be on service layer.
    @Query(
            value = "UPDATE Student s set s.guardian.name = ?2 where s.name = ?1"
    )
    int updateGuardianNameOfStudent(String firstName, String guardianName);


}
