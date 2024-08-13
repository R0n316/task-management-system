package ru.alex.taskmanagementsystem.dto;

public record TaskFilter(
        Integer authorId,
        Integer executorId
) {
}
