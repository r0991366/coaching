package be.ucll.se.courses.backend.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record LecturerInput(@NotBlank String expertise, @Valid UserInput user) {}
