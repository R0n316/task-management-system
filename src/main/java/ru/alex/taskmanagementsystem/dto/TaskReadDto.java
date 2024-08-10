package ru.alex.taskmanagementsystem.dto;

public record TaskReadDto(
        Integer id,
        String title,
        String description,
        String status,
        String priority,
        UserReadDto author,
        UserReadDto executor
) {
}
