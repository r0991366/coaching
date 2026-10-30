//package be.ucll.se.courses.backend.service;
//
//import be.ucll.se.courses.backend.controller.dto.ScheduleInput;
//import be.ucll.se.courses.backend.model.*;
//import be.ucll.se.courses.backend.repository.CourseRepository;
//import be.ucll.se.courses.backend.repository.LecturerRepository;
//import be.ucll.se.courses.backend.repository.ScheduleRepository;
//import be.ucll.se.courses.backend.repository.StudentRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith({MockitoExtension.class, SpringExtension.class})
//@ContextConfiguration
//public class ScheduleServiceTest {
//
//    @MockitoBean
//    private LecturerRepository lecturerRepository;
//
//    @MockitoBean
//    private CourseRepository courseRepository;
//
//    @MockitoBean
//    private ScheduleRepository scheduleRepository;
//
//    @MockitoBean
//    private StudentRepository studentRepository;
//
//    private ScheduleService scheduleService;
//
//    @BeforeEach
//    public void setup() {
//        scheduleService = new ScheduleService(
//                scheduleRepository,
//                courseRepository,
//                lecturerRepository,
//                studentRepository
//        );
//    }
//
//    @Test
//    public void givenValidCourseAndScheduleIDAndNoScheduleExistsYet_whenCreateSchedule_thenScheduleIsCreated() {
//        final var user = new User(
//            "josbosmans",
//                "Jos",
//                "Bosmans",
//                "jos@example.com",
//                "{noop}password",
//                Role.LECTURER
//        );
//        user.setId(1L);
//        final var lecturer = new Lecturer(
//                "Teaching",
//                user
//        );
//        lecturer.setId(1L);
//        final var course = new Course(
//                "Course 1",
//                "Course description",
//                1,
//                6
//        );
//        course.setId(1L);
//
//        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
//        Mockito.when(lecturerRepository.findById(1L)).thenReturn(Optional.of(lecturer));
//        Mockito.when(scheduleRepository.existsByCourse_IdAndLecturer_Id(1L, 1L)).thenReturn(false);
//
//        Mockito.when(scheduleRepository.save(Mockito.any())).thenAnswer(i -> {
//            final var schedule = (Schedule)i.getArgument(0);
//            schedule.setId(1L);
//            return schedule;
//        });
//
//        final var start = Instant.now();
//        final var end = Instant.now().plus(1L, ChronoUnit.HOURS);
//
//        final var schedule = scheduleService.createSchedule(new ScheduleInput(
//                start,
//                end,
//                1L,
//                1L
//        ));
//
//        Assertions.assertNotNull(schedule);
//        Assertions.assertEquals(1L, schedule.getId());
//        Assertions.assertEquals(1L, schedule.getLecturer().getId());
//        Assertions.assertEquals(1L, schedule.getCourse().getId());
//        Assertions.assertEquals(start, schedule.getStart());
//        Assertions.assertEquals(end, schedule.getEnd());
//    }
//
//    @Test
//    @WithMockUser(username = "josbosmans", roles = {"LECTURER"})
//    public void givenUserIsAuthenticatedAsLecturer_whenGetSchedule_thenScheduleIsReturned() {
//        final var schedule = Mockito.mock(Schedule.class);
//
//        Mockito.when(scheduleRepository.findByLecturer_User_Username("josbosmans")).thenReturn(List.of(schedule));
//
//        final var authentication = SecurityContextHolder.getContext().getAuthentication();
//        final var list = scheduleService.getSchedules(authentication);
//        Assertions.assertEquals(1, list.size());
//        Assertions.assertEquals(schedule, list.getFirst());
//    }
//}
