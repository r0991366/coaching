package be.ucll.fullstack.lab4.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course course;

    @BeforeEach
    void initTest() {
        course = new Course("Full Stack Development", 6);
    }

    @Test
    void settingValidName_thatNameReturned() {
        assertEquals("Full Stack Development", course.getName());
        course.setName("Full-Stack");
        assertEquals("Full-Stack", course.getName());
    }
}

