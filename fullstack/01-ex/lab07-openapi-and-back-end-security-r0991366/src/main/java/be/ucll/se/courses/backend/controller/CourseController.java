package be.ucll.se.courses.backend.controller;

import be.ucll.se.courses.backend.model.Course;
import be.ucll.se.courses.backend.service.CourseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")

public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "The course with given ID")
    @Parameter(name = "id", description = "Course ID")
    public Course getCourseById(@PathVariable long id) {
        return courseService.getCourseById(id);
    }
}


