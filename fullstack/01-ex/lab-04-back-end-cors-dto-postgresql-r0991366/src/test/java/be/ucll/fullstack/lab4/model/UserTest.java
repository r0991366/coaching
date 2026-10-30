package be.ucll.fullstack.lab4.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void initTest() {
        user = new User("Steven Engls", LocalDate.now(), Role.ADMIN);
    }

    @Test
    void settingValidName_thatNameReturned() {
        assertEquals("Steven Engls", user.getName());
        user.setName("Steven Engels");
        assertEquals("Steven Engels", user.getName());
    }
}
