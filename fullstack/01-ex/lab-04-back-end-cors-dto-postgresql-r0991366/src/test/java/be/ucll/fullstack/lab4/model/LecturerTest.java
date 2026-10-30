package be.ucll.fullstack.lab4.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class LecturerTest {
    private Lecturer lecturer;

    @BeforeEach
    void initTest() {
        lecturer = new Lecturer("John Doe", "Mathematics");
    }

    @Test
    void settingValidName_thatNameReturned() {
        assertEquals("John Doe", lecturer.getName());
        lecturer.setName("Johnny Depp");
        assertEquals("Johnny Depp", lecturer.getName());
    }
}
