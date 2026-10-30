package be.ucll.se.courses.backend.repository.springdatajpa;

import be.ucll.se.courses.backend.model.Lecturer;
import be.ucll.se.courses.backend.repository.LecturerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("!jdbc & !jpa")
public interface SpringDataJpaLecturerRepository extends JpaRepository<Lecturer, Long>, LecturerRepository {}
