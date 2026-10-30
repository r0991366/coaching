package be.ucll.se.courses.backend.repository.jpa;

import be.ucll.se.courses.backend.model.Lecturer;
import be.ucll.se.courses.backend.repository.LecturerRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaLecturerRepository implements LecturerRepository {

    private final EntityManager entityManager;

    public JpaLecturerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Lecturer save(Lecturer lecturer) {
        entityManager.persist(lecturer);
        return lecturer;
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Lecturer").executeUpdate();
    }

    @Override
    public Optional<Lecturer> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Lecturer.class, id));
    }

    @Override
    public List<Lecturer> findAll() {
        return entityManager.createQuery("select l from Lecturer l", Lecturer.class).getResultList();
    }
}
