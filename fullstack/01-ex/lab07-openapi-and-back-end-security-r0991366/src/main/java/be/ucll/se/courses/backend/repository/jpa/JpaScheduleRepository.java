package be.ucll.se.courses.backend.repository.jpa;

import be.ucll.se.courses.backend.model.Schedule;
import be.ucll.se.courses.backend.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
public class JpaScheduleRepository implements ScheduleRepository {

    private final EntityManager entityManager;

    public JpaScheduleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Schedule save(Schedule schedule) {
        entityManager.persist(schedule);
        return schedule;
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Schedule").executeUpdate();
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Schedule.class, id));
    }

    @Override
    public List<Schedule> findAll() {
        return entityManager.createQuery("select s from Schedule s", Schedule.class).getResultList();
    }

    @Override
    public List<Schedule> findByLecturer_User_Username(String username) {
        return entityManager
                .createQuery("""
                    select s from Schedule s
                    join s.lecturer l
                    join l.user u
                    where u.username = :username""", Schedule.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public boolean existsByCourse_IdAndLecturer_Id(Long courseId, Long lecturerId) {
        return !entityManager
                .createQuery("""
                    select s from Schedule s
                    where s.course.id = :courseId
                      and s.lecturer.id = :lecturerId""", Schedule.class)
                .setParameter("courseId", courseId)
                .setParameter("lecturerId", lecturerId)
                .getResultList().isEmpty();
    }
}
