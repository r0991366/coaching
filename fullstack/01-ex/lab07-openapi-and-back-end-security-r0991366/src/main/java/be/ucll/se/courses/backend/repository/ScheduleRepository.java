package be.ucll.se.courses.backend.repository;

import be.ucll.se.courses.backend.model.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule save(Schedule schedule);

    void deleteAll();

    Optional<Schedule> findById(Long id);

    List<Schedule> findAll();

    List<Schedule> findByLecturer_User_Username(String username);

    boolean existsByCourse_IdAndLecturer_Id(Long courseId, Long lecturerId);
}
