package be.ucll.fullstack.lab4.repository;

import be.ucll.fullstack.lab4.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
}