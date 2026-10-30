package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.controller.dto.StudentInput;
import be.ucll.se.courses.backend.model.Role;
import be.ucll.se.courses.backend.model.Student;
import be.ucll.se.courses.backend.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final UserService userService;
    private final StudentRepository studentRepository;

    public StudentService(UserService userService, StudentRepository studentRepository) {
        this.userService = userService;
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(StudentInput studentInput) {
        var user = userService.signup(Role.STUDENT, studentInput.user());
        return studentRepository.save(new Student(studentInput.studentNumber(), user));
    }
}
