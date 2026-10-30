package be.ucll.se.courses.backend.repository.jpa;

import be.ucll.se.courses.backend.model.Course;
import be.ucll.se.courses.backend.repository.CourseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaCourseRepository implements CourseRepository {

    private final EntityManager entityManager;

    public JpaCourseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Course save(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Course").executeUpdate();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Course.class, id));
    }

    @Override
    public List<Course> findAll() {
        return entityManager.createQuery("select c from Course c", Course.class).getResultList();
    }
}
