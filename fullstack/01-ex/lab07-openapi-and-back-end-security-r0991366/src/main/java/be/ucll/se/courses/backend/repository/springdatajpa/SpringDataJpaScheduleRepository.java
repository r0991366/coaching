package be.ucll.se.courses.backend.repository.springdatajpa;

import be.ucll.se.courses.backend.model.Schedule;
import be.ucll.se.courses.backend.repository.ScheduleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile("!jdbc & !jpa")
public interface SpringDataJpaScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepository {

    List<Schedule> findByLecturer_User_Username(String username);

    boolean existsByCourse_IdAndLecturer_Id(Long courseId, Long lecturerId);
}
