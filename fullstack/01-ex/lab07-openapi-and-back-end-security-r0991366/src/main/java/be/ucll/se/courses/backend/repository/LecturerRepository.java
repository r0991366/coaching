package be.ucll.se.courses.backend.repository;

import be.ucll.se.courses.backend.model.Lecturer;

import java.util.List;
import java.util.Optional;

public interface LecturerRepository {

    Lecturer save(Lecturer lecturer);

    void deleteAll();

    Optional<Lecturer> findById(Long id);

    List<Lecturer> findAll();
}
