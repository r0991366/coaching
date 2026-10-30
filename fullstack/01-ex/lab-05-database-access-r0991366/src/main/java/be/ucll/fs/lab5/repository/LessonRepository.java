package be.ucll.fs.lab5.repository;

import be.ucll.fs.lab5.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {}
