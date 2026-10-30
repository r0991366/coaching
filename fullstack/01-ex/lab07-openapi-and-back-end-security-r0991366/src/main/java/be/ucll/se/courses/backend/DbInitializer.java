package be.ucll.se.courses.backend;

import be.ucll.se.courses.backend.model.*;
import be.ucll.se.courses.backend.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class DbInitializer {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;
    private final TransactionTemplate transactionTemplate;
    private final PasswordEncoder passwordEncoder;

    public DbInitializer(CourseRepository courseRepository,
                         UserRepository userRepository,
                         LecturerRepository lecturerRepository,
                         StudentRepository studentRepository,
                         ScheduleRepository scheduleRepository,
                         TransactionTemplate transactionTemplate, PasswordEncoder passwordEncoder) {

        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
        this.scheduleRepository = scheduleRepository;
        this.transactionTemplate = transactionTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public void clearAll() {
        scheduleRepository.deleteAll();
        courseRepository.deleteAll();
        lecturerRepository.deleteAll();
        studentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @PostConstruct
    public void init() {
        transactionTemplate.executeWithoutResult((transactionStatus) -> {
            clearAll();

            final var fullStack = courseRepository.save(new Course(
                    "Full-stack development",
                    "Learn how to build a full stack web application.",
                    2,
                    6
            ));

            final var softwareEngineering = courseRepository.save(new Course(
                    "Software Engineering",
                    "Learn how to build and deploy a software application.",
                    2,
                    6
            ));

            final var frontEnd = courseRepository.save(new Course(
                    "Front-End Development",
                    "Learn how to build a front-end web application.",
                    1,
                    6
            ));

            final var backEnd = courseRepository.save(new Course(
                    "Back-end Development",
                    "Learn how to build a REST-API in a back-end application.",
                    1,
                    6
            ));

            final var admin = userRepository.save(new User(
                    "admin",
                    "admin",
                    "admin",
                    "administration@ucll.be",
                    passwordEncoder.encode("admin"),

                    Role.ADMIN
            ));

            // Johan Pieck

            final var userJP = userRepository.save(new User(
                    "johanp",
                    "Johan",
                    "Pieck",
                    "johan.pieck@ucll.be",
                    passwordEncoder.encode("johanp"),
                    Role.LECTURER
            ));

            final var lecturerJP = lecturerRepository.save(new Lecturer(
                    "Full-stack development, Front-end development",
                    userJP
            ));

            fullStack.addLecturer(lecturerJP);
            frontEnd.addLecturer(lecturerJP);

            // Jeroen Rombouts

            final var userES = userRepository.save(new User(
                    "jeroenr",
                    "Jeroen",
                    "Rombouts",
                    "jeroen.rombouts@ucll.be",
                    passwordEncoder.encode("jeroenr"),
                    Role.LECTURER
            ));

            final var lecturerES = lecturerRepository.save(new Lecturer(
                    "Software Engineering, Back-End Development",
                    userES
            ));

            fullStack.addLecturer(lecturerES);
            softwareEngineering.addLecturer(lecturerES);

            // Bram Van Impe

            final var userGJ = userRepository.save(new User(
                    "bramb",
                    "Bram",
                    "Van Impe",
                    "bram.vanimpe@ucll.be",
                    passwordEncoder.encode("bramb"),
                    Role.LECTURER
            ));

            final var lecturerGJ = lecturerRepository.save(new Lecturer(
                    "Full-Stack development, Back-end Development",
                    userGJ
            ));

            fullStack.addLecturer(lecturerGJ);
            backEnd.addLecturer(lecturerGJ);

            // Ruben Naudts

            final var rubenn = userRepository.save(new User(
                    "rubenn",
                    "Ruben",
                    "Naudts",
                    "ruben.naudts@ucll.be",
                    passwordEncoder.encode("rubenn"),
                    Role.LECTURER
            ));

            final var lecturerRN = lecturerRepository.save(new Lecturer(
                    "Full-Stack development, Front-end Development",
                    rubenn
            ));

            fullStack.addLecturer(lecturerRN);
            frontEnd.addLecturer(lecturerRN);

            courseRepository.save(frontEnd);
            courseRepository.save(fullStack);
            courseRepository.save(backEnd);
            courseRepository.save(softwareEngineering);

            // Student 1

            final var student1User = userRepository.save(new User(
                    "peterp",
                    "Peter",
                    "Parker",
                    "peter.parker@student.ucll.be",
                    passwordEncoder.encode("peterp123"),
                    Role.STUDENT
            ));

            final var student1 = studentRepository.save(new Student(
                    "r0785023",
                    student1User
            ));

            // Student 2

            final var student2User = userRepository.save(new User(
                    "bruceb",
                    "Bruce",
                    "Banner",
                    "bruce.banner@student.ucll.be",
                    passwordEncoder.encode("bruceb123"),
                    Role.STUDENT
            ));

            final var student2 = studentRepository.save(new Student(
                    "r0785024",
                    student2User
            ));

            // Student 3

            final var student3User = userRepository.save(new User(
                    "sallys",
                    "Sally",
                    "Smith",
                    "sally.smith@student.ucll.be",
                    passwordEncoder.encode("sallys123"),
                    Role.STUDENT
            ));

            final var student3 = studentRepository.save(new Student(
                    "r0785025",
                    student3User
            ));

            // Student 4

            final var student4User = userRepository.save(new User(
                    "michaelm",
                    "Michael",
                    "Miller",
                    "michael.miller@student.ucll.be",
                    passwordEncoder.encode("michaelm123"),
                    Role.STUDENT
            ));

            final var student4 = studentRepository.save(new Student(
                    "r0785026",
                    student4User
            ));

            // Student 5

            final var student5User = userRepository.save(new User(
                    "lindas",
                    "Linda",
                    "Lawson",
                    "linda.lawson@student.ucll.be",
                    passwordEncoder.encode("lindal123"),
                    Role.STUDENT
            ));

            final var student5 = studentRepository.save(new Student(
                    "r0785027",
                    student5User
            ));

            // Schedules

            final var schedule1 = scheduleRepository.save(new Schedule(
                    todayAt(8, 30),
                    todayAt(10, 30),
                    fullStack,
                    lecturerJP
            ));
            schedule1.addStudent(student1);
            scheduleRepository.save(schedule1);

            final var schedule2 = scheduleRepository.save(new Schedule(
                    todayAt(13, 30),
                    todayAt(15, 30),
                    fullStack,
                    lecturerES
            ));
            schedule2.addStudent(student2);
            scheduleRepository.save(schedule2);

            final var schedule3 = scheduleRepository.save(new Schedule(
                    todayAt(13, 30),
                    todayAt(15, 30),
                    softwareEngineering,
                    lecturerES
            ));
            schedule3.addStudent(student4);
            scheduleRepository.save(schedule3);

            final var schedule4 = scheduleRepository.save(new Schedule(
                    todayAt(10, 45),
                    todayAt(12, 45),
                    backEnd,
                    lecturerGJ
            ));
            schedule4.addStudent(student5);
            scheduleRepository.save(schedule4);
        });
    }

    /**
     * Utility method to quickly create an Instant that's at a certain time today
     */
    private static Instant todayAt(int hour, int minute) {
        return LocalDate.now().atTime(hour, minute).atZone(ZoneId.systemDefault()).toInstant();
    }
}
