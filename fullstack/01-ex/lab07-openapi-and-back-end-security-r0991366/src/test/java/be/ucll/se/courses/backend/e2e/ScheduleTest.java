//package be.ucll.se.courses.backend.e2e;
//
//import be.ucll.se.courses.backend.controller.dto.ScheduleInput;
//import be.ucll.se.courses.backend.model.Role;
//import be.ucll.se.courses.backend.DbInitializer;
//import be.ucll.se.courses.backend.repository.ScheduleRepository;
//import be.ucll.se.courses.backend.service.JwtService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import java.time.LocalDate;
//import java.time.ZoneOffset;
//import java.time.temporal.ChronoUnit;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//@Sql("classpath:schema.sql")
//public class ScheduleTest {
//
//    @Autowired
//    private WebTestClient client;
//
//    @Autowired
//    private ScheduleRepository scheduleRepository;
//
//    @Autowired
//    private DbInitializer dbInitializer;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @BeforeEach
//    public void setUp() {
//        dbInitializer.init();
//    }
//
//    @Test
//    public void givenValidCourseAndScheduleIDAndNoScheduleExistsYet_whenCreateSchedule_thenScheduleIsCreated() {
//        final var start = LocalDate.of(2020, 1, 1).atTime(1, 2, 3).toInstant(ZoneOffset.UTC);
//        final var end = start.plus(1L, ChronoUnit.HOURS);
//
//        final var token = jwtService.generateToken("admin", Role.ADMIN);
//
//        client
//                .post()
//                .uri("/schedules")
//                .header("Authorization", "Bearer " + token)
//                .bodyValue(new ScheduleInput(
//                        start,
//                        end,
//                        1L,
//                        3L
//                ))
//                .exchange()
//                .expectStatus().is2xxSuccessful()
//                .expectBody()
//                .json("""
//                      {
//                        "start": "2020-01-01T01:02:03Z",
//                        "end": "2020-01-01T02:02:03Z",
//                        "course": {
//                          "id": 1
//                        },
//                        "lecturer": {
//                          "id": 3
//                        }
//                      }
//                      """);
//
//        final var schedules = scheduleRepository.findByLecturer_User_Username("bramb");
//        Assertions.assertTrue(schedules.stream().anyMatch(schedule -> schedule.getCourse().getId() == 1L));
//    }
//
//    @Test
//    public void givenUserIsAuthenticatedAsLecturer_whenGetSchedule_thenScheduleIsReturned() {
//        final var token = jwtService.generateToken("bramb", Role.LECTURER);
//
//        client
//                .get()
//                .uri("/schedules")
//                .header("Authorization", "Bearer " + token)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .json("""
//                      [
//                        {
//                          "id": 4
//                        }
//                      ]
//                      """);
//    }
//
//    @Test
//    public void givenUserIsAuthenticatedAsAdmin_whenGetSchedule_thenAllSchedulesAreReturned() {
//        final var token = jwtService.generateToken("admin", Role.ADMIN);
//
//        client
//                .get()
//                .uri("/schedules")
//                .header("Authorization", "Bearer " + token)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .json("""
//                      [
//                        {
//                          "id": 1
//                        },
//                        {
//                          "id": 2
//                        },
//                        {
//                          "id": 3
//                        },
//                        {
//                          "id": 4
//                        }
//                      ]
//                      """);
//    }
//}
