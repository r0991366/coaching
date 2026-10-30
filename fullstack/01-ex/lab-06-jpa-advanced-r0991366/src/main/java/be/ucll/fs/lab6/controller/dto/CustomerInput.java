package be.ucll.fs.lab6.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CustomerInput(@NotBlank String firstName, @NotBlank String name, Boolean passedTheoryExam, @NotNull List<DrivingLicense> licenses) {

    public record DrivingLicense(Long id, String license, String transmission) {}
}
