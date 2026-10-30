package be.ucll.se.courses.backend.repository;

import be.ucll.se.courses.backend.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Course save(Course course);

    void deleteAll();

    Optional<Course> findById(Long id);

    List<Course> findAll();
}
