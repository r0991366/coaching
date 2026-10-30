package be.ucll.fs.lab5.service;

import be.ucll.fs.lab5.model.Lesson;
import be.ucll.fs.lab5.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }
}
