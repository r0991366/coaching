package be.ucll.se.courses.backend.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EnrollmentInput(
        @NotNull Long schedule,
        @Valid List<Student> students
) {
    public record Student(@NotNull Long id) {}
}
