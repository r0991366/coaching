package be.ucll.se.courses.backend.controller;

import be.ucll.se.courses.backend.controller.dto.StudentInput;
import be.ucll.se.courses.backend.model.Student;
import be.ucll.se.courses.backend.service.StudentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ApiResponse(responseCode = "200", description = "Add a new student to the database")
    @Parameter(name = "student_input", description = "A new student object")
    public Student addStudent(@RequestBody @Valid StudentInput studentInput) {
        return studentService.addStudent(studentInput);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
