package be.ucll.fs.lab5.controller.dto;

import be.ucll.fs.lab5.controller.dto.DrivingLicenseInput;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record CustomerInput(@NotBlank String firstName, @NotBlank String name, Boolean passedTheoryExam, List<DrivingLicenseInput> licenses) {}
