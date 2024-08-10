package ru.alex.taskmanagementsystem.dto;

public record AuthDto(
        String email,
        String password,
        String name
) {
}
