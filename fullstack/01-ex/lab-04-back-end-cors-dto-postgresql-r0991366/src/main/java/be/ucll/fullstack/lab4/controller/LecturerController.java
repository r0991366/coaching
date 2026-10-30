package be.ucll.fullstack.lab4.controller;

import be.ucll.fullstack.lab4.model.Lecturer;
import be.ucll.fullstack.lab4.repository.LecturerRepository;
import be.ucll.fullstack.lab4.service.LecturerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/lecturers")
public class LecturerController {

    private final LecturerRepository lecturerRepository;
    private final LecturerService lecturerService;

    public LecturerController(LecturerRepository lecturerRepository, LecturerService lecturerService) {
        this.lecturerRepository = lecturerRepository;
        this.lecturerService = lecturerService;
    }

    @GetMapping
    public List<Lecturer> getLecturers() {
        return lecturerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecturer> getLecturerById(@PathVariable Long id) {
        return lecturerRepository.findById(id)
                .map(ResponseEntity::ok)                  // 200 OK with the lecturer
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 if not found
    }

    @PostMapping
    public Lecturer addLecturer(@RequestBody @Valid LecturerInput lecturerInput) {
        return lecturerService.addLecturer(lecturerInput);
    }
}
