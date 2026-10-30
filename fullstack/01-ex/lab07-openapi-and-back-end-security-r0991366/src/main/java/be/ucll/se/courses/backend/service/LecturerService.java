package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.controller.dto.LecturerInput;
import be.ucll.se.courses.backend.exception.NotFoundException;
import be.ucll.se.courses.backend.model.Lecturer;
import be.ucll.se.courses.backend.model.Role;
import be.ucll.se.courses.backend.repository.LecturerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LecturerService {

    private final UserService userService;
    private final LecturerRepository lecturerRepository;

    public LecturerService(UserService userService, LecturerRepository lecturerRepository) {
        this.userService = userService;
        this.lecturerRepository = lecturerRepository;
    }

    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    public Lecturer getLecturerById(long id) {
        return lecturerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Lecturer with id " + id + " not found"));
    }

    public Lecturer addLecturer(LecturerInput lecturerInput) {
        var user = userService.signup(Role.LECTURER, lecturerInput.user());
        return lecturerRepository.save(new Lecturer(lecturerInput.expertise(), user));
    }
}
