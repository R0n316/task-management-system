package ru.alex.taskmanagementsystem.dto;

public record TaskCreateEditDto(
        String title,
        String description,
        Integer status,
        Integer priority,
        Integer executorId
) {
}
