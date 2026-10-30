package be.ucll.fs.lab6.controller;

import be.ucll.fs.lab6.controller.dto.LessonInput;
import be.ucll.fs.lab6.model.Lesson;
import be.ucll.fs.lab6.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping
    public Lesson createLessonWithSubject(@Valid @RequestBody LessonInput input) {
        return lessonService.createLessonWithSubject(input);
    }

    @PutMapping(value ="/{id}")
    public Lesson updateLesson(@PathVariable Long id, @Valid @RequestBody LessonInput input) {
        return lessonService.update(id, input);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        lessonService.removeLesson(id);
    }

    @GetMapping
    public List<Lesson> findAll() {
        return lessonService.findAll();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleRuntimeException(RuntimeException ex) {
        var errors = new HashMap<String, String>();
        errors.put("error", ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        for (FieldError error : ex.getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }
}
