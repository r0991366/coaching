package be.ucll.se.courses.backend.repository;

import be.ucll.se.courses.backend.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    Student save(Student student);

    void deleteAll();

    Optional<Student> findById(Long id);

    List<Student> findAll();
}
