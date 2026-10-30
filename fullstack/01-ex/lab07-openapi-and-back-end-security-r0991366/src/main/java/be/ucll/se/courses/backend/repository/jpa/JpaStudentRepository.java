package be.ucll.se.courses.backend.repository.jpa;

import be.ucll.se.courses.backend.model.Student;
import be.ucll.se.courses.backend.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaStudentRepository implements StudentRepository {

    private final EntityManager entityManager;

    public JpaStudentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Student save(Student student) {
        entityManager.persist(student);
        return student;
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Student").executeUpdate();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

    @Override
    public List<Student> findAll() {
        return entityManager.createQuery("select s from Student s", Student.class).getResultList();
    }
}
