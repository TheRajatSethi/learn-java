package com.rs.SB2;

import com.rs.SB2.entity.Guardian;
import com.rs.SB2.entity.Student;
import com.rs.SB2.repository.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sb2Application {

	public static void main(String[] args) {
		// Catch context in context variable.
		var context = SpringApplication.run(Sb2Application.class, args);

		StudentRepository studentRepository = context.getBean(StudentRepository.class);

		Guardian guardian = new Guardian("Pete Nelson", "pete.nelson@gmail.com");
		studentRepository.save(Student.builder().name("Sam Nelson").email("sam.nelson@gmail.com").guardian(guardian).build());
		studentRepository.updateGuardianNameOfStudent("Sam Nelson", "Roman Nelson");
	}

}
