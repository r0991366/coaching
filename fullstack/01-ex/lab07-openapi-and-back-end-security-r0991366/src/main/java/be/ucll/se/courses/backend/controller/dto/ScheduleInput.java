package be.ucll.se.courses.backend.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record ScheduleInput(
        @NotNull Instant start,
        @NotNull Instant end,
        @NotNull Long course,
        @NotNull Long lecturer
) {
}
