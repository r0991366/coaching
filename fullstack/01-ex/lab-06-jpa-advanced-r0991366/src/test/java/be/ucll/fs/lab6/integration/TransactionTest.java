package be.ucll.fs.lab6.integration;

import be.ucll.fs.lab6.repository.DrivingLicenseRepository;
import be.ucll.fs.lab6.repository.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql("classpath:schema.sql")
public class TransactionTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private DrivingLicenseRepository drivingLicenseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    public void givenInvalidCustomerInformation_whenCreateCustomerWithNewLicensesAndExceptionIsThrown_thenNothingIsCreated() {
        client
                .post()
                .uri("/customers")
                .header("Content-Type", "application/json")
                .bodyValue("""
                        {
                            "firstName": "Clémentine",
                            "name": "Gibben",
                            "passedTheoryExam": true,
                            "licenses": [
                              {
                                "license": "B",
                                "transmission": "Manual"
                              }
                            ]
                        }
                        """)
                .exchange()
                .expectStatus()
                .is4xxClientError();

        // Driving licenses should not be created
        final var license = drivingLicenseRepository.findById(4L);
        Assertions.assertTrue(license.isEmpty());
    }

    @Test
    public void givenValidLessonInformation_whenUpdatingLesson_thenNewInformationIsSaved() {
        client
                .put()
                .uri("/lessons/1")
                .header("Content-Type", "application/json")
                .bodyValue("""
                        {
                            "date": "2025-09-24",
                            "timeStart": "09:00:00",
                            "durationMinutes": 45
                        }
                        """)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        // Information of the lesson should be updated
        var lesson = lessonRepository.findById(1l).orElseThrow();
        Assertions.assertEquals(LocalDate.of(2025, 9, 24), lesson.getDate());
        Assertions.assertEquals(LocalTime.of(9, 0, 0), lesson.getTimeStart());
        Assertions.assertEquals(45, lesson.getDurationMinutes());
    }
}
