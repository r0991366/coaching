package be.ucll.se.courses.backend.repository.springdatajpa;

import be.ucll.se.courses.backend.model.Course;
import be.ucll.se.courses.backend.repository.CourseRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("!jdbc & !jpa")
public interface SpringDataJpaCourseRepository extends JpaRepository<Course, Long>, CourseRepository {
}
