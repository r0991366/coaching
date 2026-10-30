package be.ucll.fs.lab6.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record LessonInput(@NotNull LocalDate date, @NotNull LocalTime timeStart, @NotNull Integer durationMinutes, @Valid SubjectInput subject) {

    public record SubjectInput(@NotNull String title, @NotNull Integer maxNrOfParticipants) {}
}