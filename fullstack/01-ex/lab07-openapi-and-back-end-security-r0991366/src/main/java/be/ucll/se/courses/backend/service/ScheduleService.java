package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.controller.dto.EnrollmentInput;
import be.ucll.se.courses.backend.controller.dto.ScheduleInput;
import be.ucll.se.courses.backend.exception.CoursesException;
import be.ucll.se.courses.backend.exception.NotFoundException;
import be.ucll.se.courses.backend.model.Schedule;
import be.ucll.se.courses.backend.repository.CourseRepository;
import be.ucll.se.courses.backend.repository.LecturerRepository;
import be.ucll.se.courses.backend.repository.ScheduleRepository;
import be.ucll.se.courses.backend.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, CourseRepository courseRepository, LecturerRepository lecturerRepository, StudentRepository studentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
    }

    public List<Schedule> getSchedules() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // get roles
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isLecturer = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_LECTURER"));


        if (isAdmin) {
            return scheduleRepository.findAll();
        }
        else if (isLecturer) {
            return scheduleRepository.findByLecturer_User_Username(username);
        }

        //shouldn't make it here but just in case
        throw new AccessDeniedException("You are not authorized to view schedules.");
    }

    public Schedule createSchedule(ScheduleInput scheduleInput) {
        final long courseId = scheduleInput.course();
        final var course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course with id " + courseId + " not found")
        );
        final long lecturerId = scheduleInput.lecturer();
        final var lecturer = lecturerRepository.findById(lecturerId).orElseThrow(
                () -> new NotFoundException("Lecturer with id " + lecturerId + " not found")
        );

        final boolean existingSchedule = scheduleRepository.existsByCourse_IdAndLecturer_Id(course.getId(), lecturer.getId());

        if (existingSchedule) {
            throw new CoursesException("Schedule already exists for course with id " + courseId + " and lecturer with id " + lecturerId);
        }

        final var schedule = new Schedule(
                scheduleInput.start(),
                scheduleInput.end(),
                course,
                lecturer
        );

        return scheduleRepository.save(schedule);
    }

    public Schedule enroll(EnrollmentInput enrollmentInput) {
        final long scheduleId = enrollmentInput.schedule();
        final var schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("Schedule with id " + scheduleId + " not found")
        );

        final var students = enrollmentInput.students().stream().map(student -> {
                    final long studentId = student.id();
                    return studentRepository.findById(student.id()).orElseThrow(
                            () -> new NotFoundException("Student with id " + studentId + " not found")
                    );
        });

        students.forEach(schedule::addStudent);
        return scheduleRepository.save(schedule);
    }
}
