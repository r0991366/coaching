package be.ucll.se.courses.backend.repository.springdatajpa;

import be.ucll.se.courses.backend.model.Student;
import be.ucll.se.courses.backend.repository.StudentRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("!jdbc & !jpa")
public interface SpringDataJpaStudentRepository extends JpaRepository<Student, Long>, StudentRepository {
}
