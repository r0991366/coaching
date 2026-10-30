package be.ucll.fullstack.lab4.config;

import be.ucll.fullstack.lab4.model.Lecturer;
import be.ucll.fullstack.lab4.repository.LecturerRepository;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DbInitializer implements CommandLineRunner {

    private final LecturerRepository lecturerRepository;

    public DbInitializer(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (lecturerRepository.count() == 0) {
            lecturerRepository.save( new Lecturer("John Doe", "Mathematics"));
            lecturerRepository.save(new Lecturer("Jane Smith", "Physics"));
        }
    }
}

//@Component
//public class DbInitializer {
//
//    private final LecturerRepository lecturerRepository;
//
//    @Autowired
//    public DbInitializer(LecturerRepository lecturerRepository) {
//        this.lecturerRepository = lecturerRepository;
//    }
//
//    @PostConstruct
//    public void initialize() {
//        lecturerRepository.save(new Lecturer("John Doe", "Mathematics"));
//        lecturerRepository.save(new Lecturer("Jane Smith", "Physics"));
//    }
//}