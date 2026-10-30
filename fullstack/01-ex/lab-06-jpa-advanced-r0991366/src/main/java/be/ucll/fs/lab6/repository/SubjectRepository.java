package be.ucll.fs.lab6.repository;

import be.ucll.fs.lab6.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {}
