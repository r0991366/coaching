package be.ucll.fullstack.lab4.repository;

import be.ucll.fullstack.lab4.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
