package be.ucll.fullstack.lab4.controller;

import be.ucll.fullstack.lab4.model.Course;
import be.ucll.fullstack.lab4.model.Lecturer;
import be.ucll.fullstack.lab4.repository.CourseRepository;
import be.ucll.fullstack.lab4.repository.LecturerRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;

    public CourseController(CourseRepository courseRepository, LecturerRepository lecturerRepository) {
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
    }
    //POST /courses  or  POST /courses/{lecturerId}
    @PostMapping({ "", "/{lecturerId}" })
    public ResponseEntity<?> createCourse(
            @RequestBody Course course,
            @PathVariable(required = false) Long lecturerId) {

        //lecturerId present
        if (lecturerId != null) {
            Optional<Lecturer> optionalLecturer = lecturerRepository.findById(lecturerId);
            if (optionalLecturer.isEmpty()) {
                return ResponseEntity
                        .status(404)
                        .body("Lecturer with ID " + lecturerId + " not found.");
            }

            Lecturer lecturer = optionalLecturer.get();
            course.getLecturers().add(lecturer); // linking both sides
        }

        Course saved = courseRepository.save(course);
        return ResponseEntity
                .created(URI.create("/courses/" + saved.getId()))
                .body(saved);
    }
}