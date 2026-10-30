package be.ucll.fs.lab6.service;

import be.ucll.fs.lab6.controller.dto.LessonInput;
import be.ucll.fs.lab6.model.Lesson;
import be.ucll.fs.lab6.model.Subject;
import be.ucll.fs.lab6.repository.LessonRepository;
import be.ucll.fs.lab6.repository.SubjectRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;

    public LessonService(LessonRepository lessonRepository, SubjectRepository subjectRepository) {
        this.lessonRepository = lessonRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public Lesson createLessonWithSubject(@Valid LessonInput input) {
        var subject = new Subject(input.subject().title(), input.subject().maxNrOfParticipants());
        var lesson = new Lesson(input.date(), input.timeStart(), input.durationMinutes(), subject);
        return lessonRepository.save(lesson);
    }

    public void removeLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    public Lesson update(Long id, LessonInput input) {
        var lesson = lessonRepository.findById(id).orElseThrow();
        lesson.update(input.date(), input.timeStart(), input.durationMinutes());
        return lesson;
    }
}
