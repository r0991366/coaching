package be.ucll.fs.lab6.integration;

import be.ucll.fs.lab6.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql("classpath:schema.sql")
public class CascadeTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Test
    public void givenValidLessonAndSubject_whenCreatingLesson_thenBothAreSaved() {
        client
                .post()
                .uri("/lessons")
                .header("Content-Type", "application/json")
                .bodyValue("""
                    {
                        "date": "2025-09-24",
                        "timeStart": "09:00:00",
                        "durationMinutes": 45,
                        "subject": {
                            "title": "Confessions of a cyclist",
                            "maxNrOfParticipants": 15
                        }
                    }
                """)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .json("""
                      {
                          "id": 7,
                          "date": "2025-09-24",
                          "timeStart": "09:00:00",
                          "durationMinutes": 45,
                          "subject": {
                            "title": "Confessions of a cyclist",
                            "maxNrOfParticipants": 15
                          }
                      }
                      """);

        // Both subject and lesson should be created
        Assertions.assertTrue(lessonRepository.findById(7L).isPresent());
        Assertions.assertTrue(subjectRepository.findById(6L).isPresent());
    }

    @Test
    public void givenValidLessonId_whenDeleteLesson_thenLessonIsDeletedButAssociatedEntitiesAreNot() {
        client
                .delete()
                .uri("/lessons/6")
                .exchange()
                .expectStatus().is2xxSuccessful();

        Assertions.assertTrue(subjectRepository.findById(6L).isEmpty());

        // Associated entities should still be there (check schema.sql for IDs)
        Assertions.assertTrue(subjectRepository.findById(4L).isPresent());
        Assertions.assertTrue(classroomRepository.findById(1L).isPresent());
        Assertions.assertTrue(employeeRepository.findById(1L).isPresent());
        Assertions.assertTrue(vehicleRepository.findById(2L).isPresent());
    }
}
