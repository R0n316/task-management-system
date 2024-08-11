package ru.alex.taskmanagementsystem.dto;

public record UserRegisterDto(
        String email,
        String password,
        String name
) {
}
