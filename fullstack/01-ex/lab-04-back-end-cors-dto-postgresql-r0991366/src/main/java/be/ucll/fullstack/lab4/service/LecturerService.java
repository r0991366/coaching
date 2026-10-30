package be.ucll.fullstack.lab4.service;

import be.ucll.fullstack.lab4.controller.LecturerController;
import be.ucll.fullstack.lab4.controller.LecturerInput;
import be.ucll.fullstack.lab4.model.Lecturer;
import be.ucll.fullstack.lab4.model.Role;
import be.ucll.fullstack.lab4.repository.LecturerRepository;
import org.springframework.stereotype.Service;

@Service
public class LecturerService {
    private final UserService userService;
    private LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository, UserService userService) {
        this.lecturerRepository = lecturerRepository;
        this.userService = userService;
    }

    public Lecturer addLecturer(LecturerInput lecturerInput) {
        var user = userService.signup(lecturerInput.name(), Role.LECTURER);
        return lecturerRepository.save(new Lecturer(user, lecturerInput.expertise()));
    }
}
