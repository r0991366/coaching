package be.ucll.fs.lab6.integration;

import be.ucll.fs.lab6.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql("classpath:schema.sql")
public class OrphanRemovalTest {

    @Autowired
    private LocationService locationService;

    @Test
    void testToObserveBehaviourInConsoleLog() {
        locationService.removeFirstClassroomFromFirstLocation();
    }
}
