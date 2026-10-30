package be.ucll.fs.lab6.integration;

import be.ucll.fs.lab6.model.Lesson;
import be.ucll.fs.lab6.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql("classpath:schema.sql")
@Transactional
public class FetchTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Test
    void testToObserveBehaviourInConsoleLog() {
        List<Lesson> lessons = lessonRepository.findAll();

        for (var lesson : lessons) {
            System.out.println("--------------------");
            System.out.println("ID of the lesson: " + lesson.getId());
            System.out.println("ID of the subject: " + lesson.getSubject().getId());
            System.out.println("ID of the classroom: " + lesson.getClassroom().getId());
            System.out.println("ID of the location of the classroom: " + lesson.getClassroom().getLocation().getId());
            System.out.println("ID of the employee: " + lesson.getEmployee().getId());

            if (lesson.getVehicle() != null) {
                System.out.println("ID of the vehicle: " + lesson.getVehicle().getId());
                System.out.println("ID of the needed driving license for the vehicle: " + lesson.getVehicle().getDrivingLicense().getId());
            }
            System.out.println("--------------------");
            System.out.println("");
            System.out.println("");
        }
    }
}
