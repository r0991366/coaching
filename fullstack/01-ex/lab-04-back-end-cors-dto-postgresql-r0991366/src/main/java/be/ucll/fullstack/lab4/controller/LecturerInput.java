package be.ucll.fullstack.lab4.controller;

import jakarta.validation.constraints.NotNull;

public record LecturerInput (
        @NotNull
        String name,
        @NotNull
        String expertise ) {
}
