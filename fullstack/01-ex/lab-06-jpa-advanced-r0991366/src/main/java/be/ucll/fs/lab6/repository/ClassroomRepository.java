package be.ucll.fs.lab6.repository;

import be.ucll.fs.lab6.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {}
